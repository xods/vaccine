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
@Table(name = "vakcine_pacijenata")
public class VakcinaPacijenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "doza", nullable = false)
    private EDose doza;

    @Temporal(TemporalType.DATE)
    @Column(name = "datumVakcinacije", nullable = false)
    private Date datumVakcinacije;

    @Temporal(TemporalType.DATE)
    @Column(name = "datumKreiranja", nullable = false)
    private Date datumKreiranja = new Date();

    @Enumerated
    @Column(name = "status", nullable = false)
    private EStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vakcina_id", nullable = false)
    private Vakcina vakcina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

}
