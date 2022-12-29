package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.UserDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.repository.UserRepository;
import com.vaccines.vaccine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<User> users = userRepository.findAll();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users){
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> registration(@RequestBody UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIme(userDTO.getIme());
        user.setPrezime(userDTO.getPrezime());
        user.setDatumRodjenja(userDTO.getDatumRodjenja());
        user.setJMBG(userDTO.getJMBG());
        user.setAdresa(userDTO.getAdresa());
        user.setDatumReg(new Date());
        user.setUloga(ERole.PATIENTS);

        user = userRepository.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }
}
