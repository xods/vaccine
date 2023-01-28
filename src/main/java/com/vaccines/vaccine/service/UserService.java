package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    User save(User user);
    User getReferenceById(Long id);
    User findByEmail(String email);
}
