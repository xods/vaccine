package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.Proizvodjac;
import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.service.ProizvodjacService;
import com.vaccines.vaccine.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("vakcina")
public class VakcinaController {

    @Autowired
    VakcinaService vakcinaService;
    @Autowired
    ProizvodjacService proizvodjacService;

    @GetMapping(value = "/")
    public ResponseEntity<List<VakcinaDTO>> getAll(){
        List<Vakcina> vakcine = vakcinaService.findAll();

        // Convert categories to DTOs
        List<VakcinaDTO> vakcineDTO = new ArrayList<>();
        for (Vakcina v : vakcine) {
            vakcineDTO.add(new VakcinaDTO(v));
        }

        return new ResponseEntity<>(vakcineDTO, HttpStatus.OK);
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
    public ResponseEntity<VakcinaDTO> find(@PathVariable Long id){
        Optional<Vakcina> vakcina = vakcinaService.findById(id);


        return new ResponseEntity<>(new VakcinaDTO(vakcina.orElse(new Vakcina())), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<VakcinaDTO> saveVakcina(@RequestBody VakcinaDTO vakcinaDTO){
        Vakcina vakcina = new Vakcina();
        Optional<Proizvodjac> proizvodjac = proizvodjacService.findById(vakcinaDTO.getProizvodjacDTO().getId());

        vakcina.setNaziv(vakcinaDTO.getNaziv());
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac.orElse(new Proizvodjac()));

        vakcina = vakcinaService.save(vakcina);

        return new ResponseEntity<>(new VakcinaDTO(vakcina), HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<VakcinaDTO> updateVakcina(@RequestBody VakcinaDTO vakcinaDTO, @PathVariable("id") Long id){
        
        Vakcina vakcina = vakcinaService.findById(id).orElse(new Vakcina());
        Proizvodjac proizvodjac = proizvodjacService.findById(vakcinaDTO.getProizvodjacDTO().getId()).orElse(new Proizvodjac());

        vakcina.setNaziv(vakcinaDTO.getNaziv());
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac);
        
        vakcina = vakcinaService.save(vakcina);
        return  new ResponseEntity<>(new VakcinaDTO(vakcina), HttpStatus.OK);
    }
}
