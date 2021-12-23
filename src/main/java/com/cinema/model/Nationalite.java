package com.cinema.model;


import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the categorie database table.
 * 
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="nationalites")
@Proxy(lazy = false)
public class Nationalite  extends AbstractModel<Long>{
	private String libelle;
	
	
	
	@JsonIgnore
    @OneToMany(mappedBy = "nationalite")
    private List<Film> films;

	@OneToMany(mappedBy = "nationalite")
	@JsonIgnore
    private List<Personne> personnes;
	
	
	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	

	public List<Personne> getPersonnes() {
		return personnes;
	}


	public void setPersonnes(List<Personne> personnes) {
		this.personnes = personnes;
	}


	public List<Film> getFilms() {
		return films;
	}


	public void setFilms(List<Film> films) {
		this.films = films;
	}


	@Override
	public String toString() {
		return libelle;
	}
	
	
	


	
	
	
}
