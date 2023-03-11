package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.ProizvodjacDTO;
import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.Proizvodjac;
import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.service.ProizvodjacService;
import com.vaccines.vaccine.service.VakcinaService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vakcina")
public class VakcinaController implements ServletContextAware {

    @Autowired
    VakcinaService vakcinaService;
    @Autowired
    ProizvodjacService proizvodjacService;
    @Autowired
    ServletContext servletContext;
    private  String bURL;

    @PostConstruct
    public void init() {
        bURL = servletContext.getContextPath()+"/";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/sve")
    public ModelAndView getAll(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }
            ModelAndView rezultat = new ModelAndView("vakcine");

            List<Vakcina> vakcine = vakcinaService.findAll();
            List<VakcinaDTO> vakcineDTO = new ArrayList<>();
            for (Vakcina v : vakcine) {
                vakcineDTO.add(new VakcinaDTO(v));
            }

            rezultat.addObject("vakcine", vakcineDTO);

            return rezultat;

    }

    @GetMapping(value ="/search")
    public ResponseEntity<List<VakcinaDTO>> sorting(@RequestParam String term, @RequestParam Integer mi, @RequestParam Integer mx, @RequestParam String s, @RequestParam Boolean u){

        Sort sort = s.equals("naziv") || s.equals("proizvodjac.naziv") || s.equals("proizvodjac.drzava") || s.equals("kolicina")? Sort.by(s): null;

        if (sort != null) {
            sort = u ? sort.ascending() : sort.descending();
        }

        List<Vakcina> vakcine = vakcinaService.findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(term, term, term, sort);

        List<VakcinaDTO> vakcineDTO = new ArrayList<>();
        for (Vakcina v : vakcine) {
            if(v.getKolicina() >= mi && v.getKolicina() <= mx){
                vakcineDTO.add(new VakcinaDTO(v));
            }
        }

        return new ResponseEntity<>(vakcineDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ModelAndView find(@PathVariable Long id, HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }
        Vakcina vakcina = vakcinaService.findById(id).orElse(new Vakcina());

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();
        List<ProizvodjacDTO> proizvodjaciDTO = new ArrayList<>();
        for (Proizvodjac p : proizvodjaci) {
            proizvodjaciDTO.add(new ProizvodjacDTO(p));
        }

        ModelAndView rez = new ModelAndView("vakcina");
        rez.addObject("vakcina", new VakcinaDTO(vakcina));
        rez.addObject("proizvodjaci", proizvodjaciDTO);
        return rez;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<VakcinaDTO> saveVakcina(@RequestBody VakcinaDTO vakcinaDTO){
        Vakcina vakcina = new Vakcina();
        Optional<Proizvodjac> proizvodjac = proizvodjacService.findById(vakcinaDTO.getProizvodjac().getId());

        vakcina.setNaziv(vakcinaDTO.getNaziv());
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac.orElse(new Proizvodjac()));

        vakcina = vakcinaService.save(vakcina);

        return new ResponseEntity<>(new VakcinaDTO(vakcina), HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}")
    public ModelAndView updateVakcina(@RequestParam String naziv,
                                      @RequestParam String idP,
                                      @PathVariable("id") String id,
                                      HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            response.sendRedirect(bURL);
        }
        Vakcina vakcina = vakcinaService.findById(Long.parseLong(id)).orElse(new Vakcina());
        Proizvodjac proizvodjac = proizvodjacService.findById(Long.parseLong(idP)).orElse(new Proizvodjac());

        vakcina.setNaziv(naziv);
        vakcina.setProizvodjac(proizvodjac);
        
        vakcina = vakcinaService.save(vakcina);
        ModelAndView rez = new ModelAndView("vakcina");
        rez.addObject("vakcina", new VakcinaDTO(vakcina));
        return rez;
    }
}
