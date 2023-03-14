package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.EDose;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;

import java.util.List;
import java.util.Optional;

public interface VakcinaPacijentaService {

    List<VakcinaPacijenta> findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(Long id, EStatus status);
    VakcinaPacijenta save(VakcinaPacijenta vakcinaPacijenta);
    List<VakcinaPacijenta> findByStatus(EStatus status);
    Optional<VakcinaPacijenta> findById(Long id);
    List<VakcinaPacijenta> findByUser_IdAndStatusAndDoza(Long id, EStatus status, EDose doza);
    void saveAll(List<VakcinaPacijenta> list);
    List<VakcinaPacijenta> findByUser_ImeContainsIgnoreCaseOrUser_PrezimeContainsIgnoreCaseOrUser_JMBGContainsIgnoreCase(String ime, String prezime, String JMBG);

}
