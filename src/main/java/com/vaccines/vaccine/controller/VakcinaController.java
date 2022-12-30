package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.VakcinaDTO;
import com.vaccines.vaccine.entity.Proizvodjac;
import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.repository.ProizvodjacRepository;
import com.vaccines.vaccine.repository.VakcinaRepository;
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
    VakcinaRepository vakcinaRepository;
    @Autowired
    ProizvodjacRepository proizvodjacRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<VakcinaDTO>> getAll(){
        List<Vakcina> vakcine = vakcinaRepository.findAll();

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

        List<Vakcina> vakcine = vakcinaRepository.findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(term, term, term, sort);

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
        Optional<Vakcina> vakcina = vakcinaRepository.findById(id);


        return new ResponseEntity<>(new VakcinaDTO(vakcina.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<VakcinaDTO> saveVakcina(@RequestBody VakcinaDTO vakcinaDTO){
        Vakcina vakcina = new Vakcina();
        Optional<Proizvodjac> proizvodjac = proizvodjacRepository.findById(vakcinaDTO.getProizvodjacDTO().getId());

        vakcina.setNaziv(vakcinaDTO.getNaziv());
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac.get());

        vakcina = vakcinaRepository.save(vakcina);

        return new ResponseEntity<>(new VakcinaDTO(vakcina), HttpStatus.CREATED);
    }
    
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<VakcinaDTO> updateVakcina(@RequestBody VakcinaDTO vakcinaDTO, @PathVariable("id") Long id){
        
        Vakcina vakcina = vakcinaRepository.findById(id).get();
        Proizvodjac proizvodjac = proizvodjacRepository.findById(vakcinaDTO.getProizvodjacDTO().getId()).get();

        vakcina.setNaziv(vakcinaDTO.getNaziv());
        vakcina.setKolicina(0);
        vakcina.setProizvodjac(proizvodjac);
        
        vakcina = vakcinaRepository.save(vakcina);
        return  new ResponseEntity<>(new VakcinaDTO(vakcina), HttpStatus.OK);
    }
}
