package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.EDose;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.VakcinaPacijenta;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VakcinaPacijentaDTO implements Serializable {

    private Long id;
    private EDose doza;
    private Date datumVakcinacije;
    private Date datumKreiranja;
    private EStatus status;
    private VakcinaDTO vakcina;
    private UserDTO user;

    public VakcinaPacijentaDTO(VakcinaPacijenta vakcinaPacijenta){
        this.id = vakcinaPacijenta.getId();
        this.doza = vakcinaPacijenta.getDoza();
        this.datumVakcinacije = vakcinaPacijenta.getDatumVakcinacije();
        this.datumKreiranja = vakcinaPacijenta.getDatumKreiranja();
        this.status = vakcinaPacijenta.getStatus();
        this.vakcina = new VakcinaDTO(vakcinaPacijenta.getVakcina());
        this.user = new UserDTO(vakcinaPacijenta.getUser());
    }
}
