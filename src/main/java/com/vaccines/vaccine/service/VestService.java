package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.Vest;

import java.util.List;

public interface VestService {

    List<Vest> findAll();
    Vest save(Vest vest);
}
