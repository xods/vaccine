package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.Vakcina;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface VakcinaService {
    Optional<Vakcina> findById(Long id);
    Vakcina save(Vakcina vakcina);
    List<Vakcina> findAll();
    List<Vakcina> findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(@Nullable String naziv, @Nullable String naziv1, @Nullable String drzava, Sort sort);
}
