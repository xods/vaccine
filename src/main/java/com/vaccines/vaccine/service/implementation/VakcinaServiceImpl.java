package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.repository.VakcinaRepository;
import com.vaccines.vaccine.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VakcinaServiceImpl implements VakcinaService {

    @Autowired
    VakcinaRepository repository;

    @Override
    public Optional<Vakcina> findById(Long id){
        return repository.findById(id);
    }

    @Override
    public Vakcina save(Vakcina vakcina){
        return repository.save(vakcina);
    }

    @Override
    public List<Vakcina> findAll(){
        return repository.findAll();
    }

    @Override
    public List<Vakcina> findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(@Nullable String naziv, @Nullable String naziv1, @Nullable String drzava, Sort sort){
        return repository.findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(naziv, naziv1, drzava, sort);
    }
}
