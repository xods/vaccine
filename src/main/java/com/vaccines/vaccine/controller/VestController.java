package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.dto.VestDTO;
import com.vaccines.vaccine.entity.Vest;
import com.vaccines.vaccine.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("vest")
public class VestController {

    @Autowired
    VestService vestService;

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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<VestDTO> post(@RequestBody VestDTO vestDTO){
        Vest vest = new Vest();

        vest.setNaziv(vestDTO.getNaziv());
        vest.setSadrzaj(vestDTO.getSadrzaj());
        vest.setVremeObjavljivanja(new Date());

        vest = vestService.save(vest);

        return new ResponseEntity<>(new VestDTO(vest), HttpStatus.CREATED);
    }
}
