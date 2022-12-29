package com.vaccines.vaccine.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaccines.vaccine.entity.DnevnaStatistika;
import lombok.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DnevnaStatistikaDTO implements Serializable {

    private Long id;
    private Integer oboleli24;
    private Integer testirani24;
    private Integer ukupnoOboleli;
    private Integer hospitalizovani;
    private Integer naRespiratoru;
    private Date vremeObjavljivanja;

    public DnevnaStatistikaDTO(DnevnaStatistika statistika){
        this(statistika.getStatistika_id(),
                statistika.getOboleli24(),
                statistika.getTestirani24(),
                statistika.getUkupnoOboleli(),
                statistika.getHospitalizovani(),
                statistika.getNaRespiratoru(),
                statistika.getVremeObjavljivanja());
    }
}
