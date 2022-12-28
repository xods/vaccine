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

    public VakcinaDTO(Vakcina vakcina){
        this.id = vakcina.getId();
        this.naziv = vakcina.getNaziv();
        this.kolicina = vakcina.getKolicina();
    }
}
