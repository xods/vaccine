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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "kolicina", nullable = false)
    private Integer kolicina;

    @Column(name = "razlog", nullable = false)
    private String razlog;

    @Column(name = "datum_kreiranja", nullable = false)
    private Date datumKreiranja = new Date();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EStatus status;

    @Column(name = "napomena")
    private String napomena;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vakcina_id", nullable = false)
    private Vakcina vakcina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "kreator_id", nullable = false)
    private User kreator;
}
