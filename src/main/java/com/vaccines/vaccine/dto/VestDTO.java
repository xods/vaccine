package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.Vest;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VestDTO implements Serializable {

    private Long id;
    private String naziv;
    private String sadrzaj;
    private Date vremeObjavljivanja;

    public VestDTO(Vest vest){
        this(vest.getId(),
                vest.getNaziv(),
                vest.getSadrzaj(),
                vest.getVremeObjavljivanja());
    }
}
