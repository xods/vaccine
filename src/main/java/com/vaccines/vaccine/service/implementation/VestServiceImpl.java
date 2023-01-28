package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.Vest;
import com.vaccines.vaccine.repository.VestRepository;
import com.vaccines.vaccine.service.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VestServiceImpl implements VestService {

    @Autowired
    VestRepository repository;

    @Override
    public List<Vest> findAll() {
        return repository.findAll();
    }

    @Override
    public Vest save(Vest vest) {
        return repository.save(vest);
    }
}
