package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;

import java.util.List;

public interface VakcinaPacijentaService {

    List<VakcinaPacijenta> findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(Long id, EStatus status);
    VakcinaPacijenta save(VakcinaPacijenta vakcinaPacijenta);
}
