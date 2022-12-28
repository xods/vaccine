package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.VakcinaPacijenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("vakcinaPacijenta")
public interface VakcinaPacijentaRepository extends JpaRepository<VakcinaPacijenta, Long> {
}