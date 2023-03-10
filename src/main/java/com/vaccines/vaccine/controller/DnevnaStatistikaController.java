package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.DnevnaStatistikaDTO;
import com.vaccines.vaccine.dto.VestDTO;
import com.vaccines.vaccine.entity.DnevnaStatistika;
import com.vaccines.vaccine.entity.Vest;
import com.vaccines.vaccine.service.DnevnaStatistikaService;
import com.vaccines.vaccine.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class DnevnaStatistikaController {

    @Autowired
    DnevnaStatistikaService service;

    @Autowired
    VestService vestService;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<DnevnaStatistikaDTO> post(@RequestBody DnevnaStatistikaDTO statistikaDTO){
        DnevnaStatistika statistika = service.findByVremeObjavljivanja(new Date());

        if(statistika != null){
            return new ResponseEntity<>(new DnevnaStatistikaDTO(statistika), HttpStatus.SEE_OTHER);
        }

        statistika = new DnevnaStatistika();
        statistika.setOboleli24(statistikaDTO.getOboleli24());
        statistika.setTestirani24(statistikaDTO.getTestirani24());
        statistika.setUkupnoOboleli(statistikaDTO.getUkupnoOboleli());
        statistika.setHospitalizovani(statistikaDTO.getHospitalizovani());
        statistika.setNaRespiratoru(statistikaDTO.getNaRespiratoru());
        statistika.setVremeObjavljivanja(new Date());

        statistika = service.save(statistika);

        return new ResponseEntity<>(new DnevnaStatistikaDTO(statistika), HttpStatus.CREATED);
    }
}
