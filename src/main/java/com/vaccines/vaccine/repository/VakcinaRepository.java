package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.Vakcina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vakcina")
public interface VakcinaRepository extends JpaRepository<Vakcina, Long> {
}