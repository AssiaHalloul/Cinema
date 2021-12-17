package com.cinema.model;


import java.io.File;
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
@Table(name="galleries")
@Proxy(lazy = false)
public class Gallerie  extends AbstractModel<Long>{
	private String titre;
	private String image;
	
	

	 
	@ManyToOne @JoinColumn(name="film_id", nullable=false)
	 private Film film;
	 
	
	@ManyToOne @JoinColumn(name="evenement_id", nullable=false)
	 private Evenement evenement;

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Evenement getEvenement() {
		return evenement;
	}

	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}

	




	
}
