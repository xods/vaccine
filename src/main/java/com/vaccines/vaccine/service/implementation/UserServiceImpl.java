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
import java.util.Optional;

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

    @Override
    public User getReferenceById(Long id){
        return repository.getReferenceById(id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password){return repository.findByEmailAndPassword(email,password);}
}
