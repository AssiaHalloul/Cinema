package com.cinema.model;


import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name="personnes")
@Proxy(lazy = false)
public class Personne  extends AbstractModel<Long>{
	private String nom;
	private String  prenom;
	
//	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date date_naissance;
	
	@Enumerated(EnumType.STRING)
	private roleEnum type;
	
	public enum roleEnum {
	    acteur, realisateur
	}
	
	
	@JsonIgnore
	@ManyToMany(mappedBy = "acteurs")
	private Set<Film> acteurfilms;
	
	@JsonIgnore
    @OneToMany(mappedBy = "realisateur")
    private List<Film> realisateurfilms;
    

//    @ManyToOne
//    @JoinColumn(name = "nationalite_id")
//    private Nationalite nationalite;
    
    @ManyToOne
    @JoinColumn(name = "nationalite_id", referencedColumnName = "id")
    private Nationalite nationalite;
    
    
    



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	public Date getDate_naissance() {
		return date_naissance;
	}



	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}



	public roleEnum getType() {
		return type;
	}



	public void setType(roleEnum type) {
		this.type = type;
	}



	public Set<Film> getActeurfilms() {
		return acteurfilms;
	}



	public void setActeurfilms(Set<Film> acteurfilms) {
		this.acteurfilms = acteurfilms;
	}



	public List<Film> getRealisateurfilms() {
		return realisateurfilms;
	}



	public void setRealisateurfilms(List<Film> realisateurfilms) {
		this.realisateurfilms = realisateurfilms;
	}



	



	public Nationalite getNationalite() {
		return nationalite;
	}



	public void setNationalite(Nationalite nationalite) {
		this.nationalite = nationalite;
	}



	@Override
	public String toString() {
		return  nom +" "+prenom ;
	}
	
	
	
	
	

	

}
