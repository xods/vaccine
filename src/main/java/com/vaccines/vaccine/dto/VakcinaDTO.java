package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.Vakcina;
import lombok.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VakcinaDTO implements Serializable {

    private Long id;
    private String naziv;
    private Integer kolicina;
    private ProizvodjacDTO proizvodjacDTO;

    public VakcinaDTO(Vakcina vakcina){
        this(vakcina.getId(),
                vakcina.getNaziv(),
                vakcina.getKolicina(),
                new ProizvodjacDTO(vakcina.getProizvodjac()));
    }
}
