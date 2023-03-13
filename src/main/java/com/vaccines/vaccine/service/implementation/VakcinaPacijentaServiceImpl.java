package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import com.vaccines.vaccine.repository.VakcinaPacijentaRepository;
import com.vaccines.vaccine.service.VakcinaPacijentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VakcinaPacijentaServiceImpl implements VakcinaPacijentaService {

    @Autowired
    VakcinaPacijentaRepository repository;

    public List<VakcinaPacijenta> findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(Long id, EStatus status){
        return repository.findByUser_IdAndStatusOrderByDatumVakcinacijeAsc(id, status);
    }

    public VakcinaPacijenta save (VakcinaPacijenta vakcinaPacijenta){
        return repository.save(vakcinaPacijenta);
    }
}
