package com.vaccines.vaccine.service;

import com.vaccines.vaccine.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    User save(User user);
}
