package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.EStatus;
import com.vaccines.vaccine.entity.Order;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO implements Serializable {

    private Long id;
    private Integer kolicina;
    private String razlog;
    private Date datumKreiranja;
    private EStatus status;
    private String napomena;
    private VakcinaDTO vakcina;
    private UserDTO kreator;

    public OrderDTO (Order order){
        this.id = order.getId();
        this.kolicina = order.getKolicina();
        this.razlog = order.getRazlog();
        this.datumKreiranja = order.getDatumKreiranja();
        this.status = order.getStatus();
        this.napomena = order.getNapomena();
        this.vakcina = new VakcinaDTO(order.getVakcina());
        this.kreator = new UserDTO(order.getKreator());
    }
}
