package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.ProizvodjacDTO;
import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.*;
import com.vaccines.vaccine.service.ProizvodjacService;
import com.vaccines.vaccine.service.VakcinaPacijentaService;
import com.vaccines.vaccine.service.VakcinaService;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    VakcinaPacijentaService vakcinaPacijentaService;
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
        if(session.getAttribute("user") == null){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
        }
        User user = (User) session.getAttribute("user");
        List<VakcinaPacijenta> listaPrimljenih = vakcinaPacijentaService.findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(user.getId(), EStatus.APPROVED);
        int brDoza = listaPrimljenih.size();

        ModelAndView rezultat = new ModelAndView("vakcine");

        List<Vakcina> vakcine = vakcinaService.findAll();
        List<VakcinaDTO> vakcineDTO = new ArrayList<>();
        for (Vakcina v : vakcine) {
            vakcineDTO.add(new VakcinaDTO(v));
        }

        rezultat.addObject("vakcine", vakcineDTO);
        rezultat.addObject("brDoza", brDoza);

        session.setAttribute("message", "");
        return rezultat;
    }

    @PostMapping(value ="/search")
    public ModelAndView sorting(@RequestParam String term,
                                @RequestParam Integer mi,
                                @RequestParam Integer mx,
                                @RequestParam String s,
                                @RequestParam @Nullable Boolean u,
                                HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
        }

        Sort sort = s.equals("naziv") || s.equals("proizvodjac.naziv") || s.equals("proizvodjac.drzava") || s.equals("kolicina")? Sort.by(s): null;

        u = (u != null) ? u : false;

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

        ModelAndView rez = new ModelAndView("vakcine");
        rez.addObject("vakcine", vakcineDTO);

        session.setAttribute("message", "");
        return rez;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView find(@PathVariable Long id, HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            session.setAttribute("message", "Nod");
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

        session.setAttribute("message", "");
        return rez;
    }

    @GetMapping(value = "/add")
    public ModelAndView add(HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
        }

        List<Proizvodjac> proizvodjaci = proizvodjacService.findAll();
        List<ProizvodjacDTO> proizvodjacDTOS = new ArrayList<>();
        for (Proizvodjac p: proizvodjaci) {
            proizvodjacDTOS.add(new ProizvodjacDTO(p));
        }

        ModelAndView rez = new ModelAndView("vakcinaAdd");
        rez.addObject("proizvodjaci", proizvodjacDTOS);

        session.setAttribute("message", "");
        return rez;
    }

    @PostMapping(value = "/add")
    public void saveVakcina(@RequestParam String pId,
                            @RequestParam String naziv,
                            HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
            return;
        }

        Vakcina vakcina = new Vakcina();
        Optional<Proizvodjac> proizvodjac = proizvodjacService.findById(Long.valueOf(pId));

        vakcina.setNaziv(naziv);
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac.orElse(new Proizvodjac()));

        vakcinaService.save(vakcina);

        session.setAttribute("message", "");
        response.sendRedirect(bURL + "vakcina/sve");
    }
    
    @PostMapping(value = "/{id}")
    public void updateVakcina(@RequestParam String naziv,
                              @RequestParam String idP,
                              @PathVariable("id") String id,
                              HttpSession session, HttpServletResponse response) throws IOException {

        if(session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
            return;
        }
        Vakcina vakcina = vakcinaService.findById(Long.parseLong(id)).orElse(new Vakcina());
        Proizvodjac proizvodjac = proizvodjacService.findById(Long.parseLong(idP)).orElse(new Proizvodjac());

        vakcina.setNaziv(naziv);
        vakcina.setProizvodjac(proizvodjac);
        
        vakcinaService.save(vakcina);

        session.setAttribute("message", "");
        response.sendRedirect(bURL + "vakcina/sve");
    }
}
