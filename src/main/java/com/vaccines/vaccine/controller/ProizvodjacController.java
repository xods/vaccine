package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.ProizvodjacDTO;
import com.vaccines.vaccine.entity.*;
import com.vaccines.vaccine.service.ProizvodjacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/proizvodjac")
public class ProizvodjacController {

    @Autowired
    ProizvodjacService service;

    @GetMapping("/")
    public ResponseEntity<List<ProizvodjacDTO>> all(){
        List<Proizvodjac> proizvodjaci = service.findAll();

        List<ProizvodjacDTO> proizvodjacDTO = new ArrayList<>();
        for(Proizvodjac p : proizvodjaci){
            proizvodjacDTO.add(new ProizvodjacDTO(p));
        }

        return new ResponseEntity<>(proizvodjacDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProizvodjacDTO> getOne(@PathVariable("id") Long id){
        Proizvodjac proizvodjac = service.findById(id).orElse(new Proizvodjac());

        return new ResponseEntity<>(new ProizvodjacDTO(proizvodjac), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ProizvodjacDTO> add(@RequestBody ProizvodjacDTO proizvodjacDTO) {
        Proizvodjac proizvodjac = new Proizvodjac();

        proizvodjac.setNaziv(proizvodjacDTO.getNaziv());
        proizvodjac.setDrzava(proizvodjacDTO.getDrzava());

        proizvodjac = service.save(proizvodjac);

        return new ResponseEntity<>(new ProizvodjacDTO(proizvodjac), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/{id}")
    public ResponseEntity<ProizvodjacDTO> update(@RequestBody ProizvodjacDTO proizvodjacDTO, @PathVariable("id") Long id){
        Proizvodjac proizvodjac = service.findById(id).orElse(new Proizvodjac());

        proizvodjac.setNaziv(proizvodjacDTO.getNaziv());
        proizvodjac.setDrzava(proizvodjacDTO.getDrzava());

        proizvodjac = service.save(proizvodjac);

        return new ResponseEntity<>(new ProizvodjacDTO(proizvodjac), HttpStatus.OK);
    }
}
