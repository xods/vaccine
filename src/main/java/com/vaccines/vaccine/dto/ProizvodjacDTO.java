package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.Proizvodjac;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProizvodjacDTO implements Serializable {

    private Long id;
    private String naziv;
    private String drzava;

    public ProizvodjacDTO(Proizvodjac proizvodjac){
        this.id = proizvodjac.getId();
        this.naziv = proizvodjac.getNaziv();
        this.drzava = proizvodjac.getDrzava();
    }
}
