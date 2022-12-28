package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.ERole;
import com.vaccines.vaccine.entity.User;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO implements Serializable {

    private Long id;
    private String email;
    private String password;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String JMBG;
    private String adresa;
    private Date datumRegistracije;
    private ERole uloga;

    public UserDTO(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.ime = user.getIme();
        this.prezime = user.getPrezime();
        this.datumRodjenja = user.getDatumRodjenja();
        this.JMBG = user.getJMBG();
        this.adresa = user.getAdresa();
        this.datumRegistracije = user.getDatumReg();
        this.uloga = user.getUloga();
    }
}
