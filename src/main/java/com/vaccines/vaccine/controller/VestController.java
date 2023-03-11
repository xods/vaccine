package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.dto.VestDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.Vest;
import com.vaccines.vaccine.service.VestService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/vest")
public class VestController implements ServletContextAware {

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
    public ResponseEntity<List<VestDTO>> getAll(){
        List<Vest> vesti = vestService.findAll();

        // Convert categories to DTOs
        List<VestDTO> vestiDTO = new ArrayList<>();
        for (Vest v : vesti) {
            vestiDTO.add(new VestDTO(v));
        }

        return new ResponseEntity<>(vestiDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public void post(@RequestParam String naziv,
                     @RequestParam String sadrzaj,
                     HttpSession session, HttpServletResponse response) throws IOException {
        if(session.getAttribute("user") == null || session.getAttribute("role") == ERole.PATIENTS.toString()){
            response.sendRedirect(bURL);
        }
        Vest vest = new Vest();

        vest.setNaziv(naziv);
        vest.setSadrzaj(sadrzaj);
        vest.setVremeObjavljivanja(new Date());

        vestService.save(vest);

        response.sendRedirect(bURL);
    }
}
