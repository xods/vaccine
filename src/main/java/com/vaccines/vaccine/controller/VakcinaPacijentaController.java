package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.entity.*;
import com.vaccines.vaccine.service.UserService;
import com.vaccines.vaccine.service.VakcinaPacijentaService;
import com.vaccines.vaccine.service.VakcinaService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mojaVakcina")
public class VakcinaPacijentaController implements ServletContextAware {

    @Autowired
    VakcinaPacijentaService vakcinaPacijentaService;
    @Autowired
    VakcinaService vakcinaService;
    @Autowired
    UserService userService;
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

    @GetMapping(value = "/{id}")
    public void add(@PathVariable("id") String id, HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || (session.getAttribute("role") != ERole.PATIENTS.toString())){
            response.sendRedirect(bURL);
        }
        User user = (User) session.getAttribute("user");
        List<VakcinaPacijenta> primljene = vakcinaPacijentaService.findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(user.getId(), EStatus.APPROVED);
        int brPrimljenih = primljene.size();

        VakcinaPacijenta poslednjaPrimljena = new VakcinaPacijenta();

        if(brPrimljenih >= 4){
            response.sendRedirect(bURL);
        }else if (brPrimljenih != 0) {
            poslednjaPrimljena = primljene.get(brPrimljenih-1);
        }

        int uslov = brPrimljenih > 0 ? EDose.retDelay(poslednjaPrimljena.getDoza()) : 0;

        EDose dose = EDose.retDose(brPrimljenih+1);

        Date datumKreiranja = new Date();

        Date datumVakcinacije = brPrimljenih > 0 ? (Date) poslednjaPrimljena.getDatumVakcinacije().clone() : new Date();

        if(brPrimljenih > 0){
            datumVakcinacije.setMonth(poslednjaPrimljena.getDatumVakcinacije().getMonth() + uslov);
        }


        Vakcina vakcina = vakcinaService.findById(Long.valueOf(id)).orElse(new Vakcina());

        VakcinaPacijenta vakcinaPacijenta = new VakcinaPacijenta();
        vakcinaPacijenta.setDoza(dose);
        vakcinaPacijenta.setDatumVakcinacije(datumVakcinacije);
        vakcinaPacijenta.setDatumKreiranja(datumKreiranja);
        vakcinaPacijenta.setStatus(EStatus.CREATED);
        vakcinaPacijenta.setVakcina(vakcina);
        vakcinaPacijenta.setUser(user);

        vakcinaPacijentaService.save(vakcinaPacijenta);

        response.sendRedirect(bURL);
    }
}
