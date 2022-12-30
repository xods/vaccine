package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.Vakcina;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vakcina")
public interface VakcinaRepository extends JpaRepository<Vakcina, Long> {
    List<Vakcina> findByNazivContainsIgnoreCaseOrProizvodjac_NazivContainsIgnoreCaseOrProizvodjac_DrzavaContainsIgnoreCase(@Nullable String naziv, @Nullable String naziv1, @Nullable String drzava, Sort sort);
}