package com.vaccines.vaccine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vakcine")
public class Vakcina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vakcina_id", nullable = false)
    private Long vakcina_id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "kolicina", nullable = false)
    private Integer kolicina;

    @ManyToOne
    @JoinColumn(name = "proizvodjac_id", referencedColumnName = "proizvodjac_id")
    private Proizvodjac proizvodjac;
}
