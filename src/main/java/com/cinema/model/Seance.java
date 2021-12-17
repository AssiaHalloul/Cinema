package com.cinema.model;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

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
@Table(name="seances")
@Proxy(lazy = false)
public class Seance  extends AbstractModel<Long>{
	private Date date;

	
	   
    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;
    
    @ManyToOne
    @JoinColumn(name = "evenement_id", referencedColumnName = "id")
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
