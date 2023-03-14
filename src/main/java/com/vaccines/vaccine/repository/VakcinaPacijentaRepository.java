package com.vaccines.vaccine.repository;

import com.vaccines.vaccine.entity.EDose;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("vakcinaPacijenta")
public interface VakcinaPacijentaRepository extends JpaRepository<VakcinaPacijenta, Long> {
    List<VakcinaPacijenta> findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(Long id, EStatus status);

    List<VakcinaPacijenta> findByStatus(EStatus status);

    List<VakcinaPacijenta> findByUser_IdAndStatusAndDoza(Long id, EStatus status, EDose doza);

    List<VakcinaPacijenta> findByUser_ImeContainsIgnoreCaseOrUser_PrezimeContainsIgnoreCaseOrUser_JMBGContainsIgnoreCase(String ime, String prezime, String JMBG);

}