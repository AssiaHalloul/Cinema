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
@Table(name="films")
@Proxy(lazy = false)
public class Film  extends AbstractModel<Long>{
	private String titre;
	private int duree;
	private String description;
	private Date annee;
	private String statue;
	private String poster;
	private String trailer;



@OneToMany(mappedBy = "film")
@JsonIgnore
private List<Gallerie> galleries;



@ManyToMany
@JoinTable(
  name = "film_acteur", 
  joinColumns = @JoinColumn(name = "film_id"), 
  inverseJoinColumns = @JoinColumn(name = "personne_id"))
private Set<Personne> acteurs;


@ManyToOne
@JoinColumn(name = "realisateur_id", referencedColumnName = "id")
private Personne realisateur;


@ManyToOne
@JoinColumn(name = "nationalite_id", referencedColumnName = "id")
private Nationalite nationalite;



@ManyToOne
@JoinColumn(name = "genre_id", referencedColumnName = "id")
private Genre genre;

@OneToMany(mappedBy = "film")
@JsonIgnore
private List<Seance> seances;



@OneToMany(mappedBy = "film")
@JsonIgnore
private List<Evenement> evenements;



public String getTitre() {
	return titre;
}



public void setTitre(String titre) {
	this.titre = titre;
}



public int getDuree() {
	return duree;
}



public void setDuree(int duree) {
	this.duree = duree;
}



public String getDescription() {
	return description;
}



public void setDescription(String description) {
	this.description = description;
}



public Date getAnnee() {
	return annee;
}



public void setAnnee(Date annee) {
	this.annee = annee;
}



public String getStatue() {
	return statue;
}



public void setStatue(String statue) {
	this.statue = statue;
}



public String getPoster() {
	return poster;
}



public void setPoster(String poster) {
	this.poster = poster;
}



public String getTrailer() {
	return trailer;
}



public void setTrailer(String trailer) {
	this.trailer = trailer;
}



public List<Gallerie> getGalleries() {
	return galleries;
}



public void setGalleries(List<Gallerie> galleries) {
	this.galleries = galleries;
}






public Genre getGenre() {
	return genre;
}



public void setGenre(Genre genre) {
	this.genre = genre;
}



public Set<Personne> getActeurs() {
	return acteurs;
}



public void setActeurs(Set<Personne> acteurs) {
	this.acteurs = acteurs;
}



public Personne getRealisateur() {
	return realisateur;
}



public void setRealisateur(Personne realisateur) {
	this.realisateur = realisateur;
}



public Nationalite getNationalite() {
	return nationalite;
}



public void setNationalite(Nationalite nationalite) {
	this.nationalite = nationalite;
}



public List<Seance> getSeances() {
	return seances;
}



public void setSeances(List<Seance> seances) {
	this.seances = seances;
}



public List<Evenement> getEvenements() {
	return evenements;
}



public void setEvenements(List<Evenement> evenements) {
	this.evenements = evenements;
}








}