package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.Proizvodjac;

import java.util.Optional;

public interface ProizvodjacService {

    Optional<Proizvodjac> findById(Long id);
}
