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
@Table(name="typeEvents")
@Proxy(lazy = false)
public class TypeEvent  extends AbstractModel<Long>{
	private String type_event;
	
//	@ManyToMany(mappedBy = "types")
//	Set<Evenement> evenement;
	
	@JsonIgnore
    @OneToMany(mappedBy = "typeEvent")
    private List<Evenement> evenements;
	

	public String getType_event() {
		return type_event;
	}

	public void setType_event(String type_event) {
		this.type_event = type_event;
	}

	public List<Evenement> getEvenements() {
		return evenements;
	}

	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}
	
	


}
