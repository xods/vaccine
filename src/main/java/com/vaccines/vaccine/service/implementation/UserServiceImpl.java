package com.vaccines.vaccine.service.implementation;

import com.vaccines.vaccine.entity.DnevnaStatistika;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.repository.UserRepository;
import com.vaccines.vaccine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;
    @Override
    public List<User> findAll(){
        return repository.findAll();
    }

    @Override
    public User save(User user){
        return repository.save(user);
    }
}
