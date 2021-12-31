package com.cinema.model;


import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="sceances")
@Proxy(lazy = false)
public class Seance  extends AbstractModel<Long>{
	
    @DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
    private Date date;

	
	   
    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id", nullable=true)
    private Film film;
    
    @ManyToOne
    @JoinColumn(name = "evenement_id", referencedColumnName = "id", nullable=true)
    private Evenement evenement;
    
	   
	 @ManyToOne
	 @JoinColumn(name = "salle_id", referencedColumnName = "id")
	 private Salle salle;


	public Date getDate() {
		return date;
	}
	
	
	public void setDate(Date date) {
		this.date = date;
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
	
	
	public Salle getSalle() {
		return salle;
	}
	
	
	public void setSalle(Salle salle) {
		this.salle = salle;
	}
		
		

	
	
	
	

	

}
