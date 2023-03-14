package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.EDose;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import com.vaccines.vaccine.repository.VakcinaPacijentaRepository;
import com.vaccines.vaccine.service.VakcinaPacijentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<VakcinaPacijenta> findByStatus(EStatus status) {
        return  repository.findByStatus(status);
    }

    public Optional<VakcinaPacijenta> findById(Long id){
        return repository.findById(id);
    }
    public List<VakcinaPacijenta> findByUser_IdAndStatusAndDoza(Long id, EStatus status, EDose doza) {

        return repository.findByUser_IdAndStatusAndDoza(id, status, doza);
    }

    public void saveAll(List<VakcinaPacijenta> list) {
        repository.saveAll(list);
    }

    public List<VakcinaPacijenta> findByUser_ImeContainsIgnoreCaseOrUser_PrezimeContainsIgnoreCaseOrUser_JMBGContainsIgnoreCase(String ime, String prezime, String JMBG){
        return repository.findByUser_ImeContainsIgnoreCaseOrUser_PrezimeContainsIgnoreCaseOrUser_JMBGContainsIgnoreCase(ime, prezime, JMBG);
    }
    public List<VakcinaPacijenta> findByIdAndStatus(Long id, EStatus status){
        return repository.findByIdAndStatus(id, status);
    }
}
