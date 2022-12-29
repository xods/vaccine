package com.vaccines.vaccine.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "ime")
	private String ime;
	
	@Column(name = "prezime")
	private String prezime;
	
	@Column(name = "datum_rodjenja")
	private Date datumRodjenja;
	
	@Column(name = "jmbg")
	private String JMBG;
	
	@Column(name = "adresa")
	private String adresa;
	
	@Column(name = "datum_registracije")
	private Date datumReg;

	@Enumerated(EnumType.STRING)
	@Column(name = "uloga")
	private ERole uloga;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<VakcinaPacijenta> vakcine = new java.util.LinkedHashSet<>();
}
