package com.vaccines.vaccine.controller;

import com.vaccines.vaccine.dto.UserDTO;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.User;
import com.vaccines.vaccine.security.TokenUtils;
import com.vaccines.vaccine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    TokenUtils tokenUtils;

    /*/
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<User> users = userService.findAll();

        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users){
            usersDTO.add(new UserDTO(u));
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }*/

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());
            return ResponseEntity.ok(tokenUtils.generateToken(userDetails));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> registration(@RequestBody UserDTO userDTO){
        User user;

        user = userService.findByEmail(userDTO.getEmail());
        if (!(user == null)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setIme(userDTO.getIme());
        user.setPrezime(userDTO.getPrezime());
        user.setDatumRodjenja(userDTO.getDatumRodjenja());
        user.setJMBG(userDTO.getJMBG());
        user.setAdresa(userDTO.getAdresa());
        user.setDatumReg(new Date());
        user.setUloga(ERole.PATIENTS);

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }
}
