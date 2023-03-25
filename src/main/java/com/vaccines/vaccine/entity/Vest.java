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
@Table(name = "Vesti")
public class Vest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vest_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "sadrzaj", nullable = false)
    private String sadrzaj;

    @Column(name = "vremeObjavljivanja", nullable = false)
    private Date vremeObjavljivanja = new Date();
}
