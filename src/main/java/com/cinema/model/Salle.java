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
@Table(name="salles")
@Proxy(lazy = false)
public class Salle  extends AbstractModel<Long>{
	private int num;
	private int nombre_places;

	
	  @OneToMany(mappedBy = "salle")
	  @JsonIgnore
	  private List<Seance> seances;


	public int getNum() {
		return num;
	}
	
	
	public void setNum(int num) {
		this.num = num;
	}
	
	
	public int getNombre_places() {
		return nombre_places;
	}
	
	
	public void setNombre_places(int nombre_places) {
		this.nombre_places = nombre_places;
	}
	
	
	public List<Seance> getSeances() {
		return seances;
	}
	
	
	public void setSeances(List<Seance> seances) {
		this.seances = seances;
	}

	
	


	
	
	
	

	

}
