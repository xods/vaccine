package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.Proizvodjac;
import com.vaccines.vaccine.repository.ProizvodjacRepository;
import com.vaccines.vaccine.service.ProizvodjacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProizvodjacServiceImpl implements ProizvodjacService {

    @Autowired
    ProizvodjacRepository repository;

    @Override
    public Optional<Proizvodjac> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Proizvodjac> findAll() {
        return repository.findAll();
    }

    @Override
    public Proizvodjac save(Proizvodjac proizvodjac) {
        return repository.save(proizvodjac);
    }
}
