package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.DnevnaStatistikaDTO;
import com.vaccines.vaccine.entity.DnevnaStatistika;
import com.vaccines.vaccine.service.DnevnaStatistikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("statistika")
public class DnevnaStatistikaController {

    @Autowired
    DnevnaStatistikaService service;

    @GetMapping
    public ResponseEntity<DnevnaStatistikaDTO> getDanas(){
        DnevnaStatistika statistika = service.findByVremeObjavljivanja(new Date());

        if(statistika != null){
            return new ResponseEntity<>(new DnevnaStatistikaDTO(statistika), HttpStatus.OK);
        }

        return new ResponseEntity<>(new DnevnaStatistikaDTO(), HttpStatus.NOT_FOUND);
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
