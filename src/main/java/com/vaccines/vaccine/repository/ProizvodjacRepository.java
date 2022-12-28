package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.Proizvodjac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("proizvodjac")
public interface ProizvodjacRepository extends JpaRepository<Proizvodjac, Long> {
}