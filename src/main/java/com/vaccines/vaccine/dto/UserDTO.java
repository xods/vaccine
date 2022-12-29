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
        this(user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getIme(),
                user.getPrezime(),
                user.getDatumRodjenja(),
                user.getJMBG(),
                user.getAdresa(),
                user.getDatumReg(),
                user.getUloga());
    }
}
