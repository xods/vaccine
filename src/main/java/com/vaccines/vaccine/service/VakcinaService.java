package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.Vakcina;

import java.util.Optional;

public interface VakcinaService {
    Optional<Vakcina> findById(Long id);
    Vakcina save(Vakcina vakcina);
}
