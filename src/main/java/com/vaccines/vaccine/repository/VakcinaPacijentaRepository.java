package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vakcinaPacijenta")
public interface VakcinaPacijentaRepository extends JpaRepository<VakcinaPacijenta, Long> {
    List<VakcinaPacijenta> findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(Long id, EStatus status);

}