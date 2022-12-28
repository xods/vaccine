package com.vaccines.vaccine.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "proizvodjaci")
public class Proizvodjac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proizvodjac_id", nullable = false)
    private Long id;

    @Column(name = "naziv", unique = true)
    private String naziv;

    @Column(name = "drzava", nullable = false)
    private String drzava;

    @OneToMany(mappedBy = "proizvodjac", cascade = CascadeType.ALL)
    private Set<Vakcina> vakcine = new HashSet<>();

}
