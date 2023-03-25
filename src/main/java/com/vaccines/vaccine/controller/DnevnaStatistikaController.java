package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.VestDTO;
import com.vaccines.vaccine.entity.DnevnaStatistika;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.Vest;
import com.vaccines.vaccine.service.DnevnaStatistikaService;
import com.vaccines.vaccine.service.VestService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class DnevnaStatistikaController implements ServletContextAware {

    @Autowired
    DnevnaStatistikaService service;
    @Autowired
    VestService vestService;
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

    @GetMapping
    public ModelAndView getDanas(){
        ModelAndView rezultat = new ModelAndView("index");

        DnevnaStatistika statistika = service.findByVremeObjavljivanja(new Date());
        rezultat.addObject("statistika", statistika);

        List<Vest> vesti = vestService.findAll();

        // Convert categories to DTOs
        List<VestDTO> vestiDTO = new ArrayList<>();
        for (Vest v : vesti) {
            vestiDTO.add(new VestDTO(v));
        }
        rezultat.addObject("vesti", vestiDTO);

        return rezultat;
    }

    @PostMapping(value = "/add")
    public void post(@RequestParam String oboleli24,
                     @RequestParam String testirani24,
                     @RequestParam String ukupno,
                     @RequestParam String hospitalizovani,
                     @RequestParam String respirator,
                     HttpSession session, HttpServletResponse response) throws IOException {
        DnevnaStatistika statistika = service.findByVremeObjavljivanja(new Date());

        if(statistika != null || session.getAttribute("user") == null || session.getAttribute("role") != ERole.ADMIN.toString()){
            session.setAttribute("message", "Nod");
            response.sendRedirect(bURL);
            return;
        }

        statistika = new DnevnaStatistika();
        statistika.setOboleli24(Integer.valueOf(oboleli24));
        statistika.setTestirani24(Integer.valueOf(testirani24));
        statistika.setUkupnoOboleli(Integer.valueOf(ukupno));
        statistika.setHospitalizovani(Integer.valueOf(hospitalizovani));
        statistika.setNaRespiratoru(Integer.valueOf(respirator));
        statistika.setVremeObjavljivanja(new Date());

         service.save(statistika);

        session.setAttribute("message", "");
        response.sendRedirect(bURL);
    }
}
