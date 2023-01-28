package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.Vakcina;
import com.vaccines.vaccine.repository.VakcinaRepository;
import com.vaccines.vaccine.service.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
