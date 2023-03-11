package com.vaccines.vaccine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DnevneStatistike")
public class DnevnaStatistika {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "statistika_id", nullable = false)
    private Long statistika_id;

    @Column(name = "oboleli_24", nullable = false)
    private Integer oboleli24;

    @Column(name = "testirani_24", nullable = false)
    private Integer testirani24;

    @Column(name = "ukupno_oboleli", nullable = false)
    private Integer ukupnoOboleli;

    @Column(name = "hospitalizovani", nullable = false)
    private Integer hospitalizovani;

    @Column(name = "na_respiratoru", nullable = false)
    private Integer naRespiratoru;

    @Temporal(TemporalType.DATE)
    @Column(name = "vreme_objavljivanja", nullable = false)
    private Date vremeObjavljivanja;
}
