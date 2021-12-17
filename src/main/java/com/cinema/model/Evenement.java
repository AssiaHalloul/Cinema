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
@Table(name="evenements")
@Proxy(lazy = false)
public class Evenement  extends AbstractModel<Long>{
	private String titre;
	private Integer duree;
	private String description;
	private Date date;
	private String poster;

	
	
   
	
	@ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "id")
    private Film film;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "evenement")
	private List<Seance> seances;
	

         @JsonIgnore
		@OneToMany(mappedBy="evenement")
		private List<Gallerie> galleries;
	   
   
	   
	    @ManyToOne
	    @JoinColumn(name = "type_id", referencedColumnName = "id")
	    private TypeEvent typeEvent;



		public String getTitre() {
			return titre;
		}



		public void setTitre(String titre) {
			this.titre = titre;
		}



		public Integer getDuree() {
			return duree;
		}



		public void setDuree(Integer duree) {
			this.duree = duree;
		}



		public String getDescription() {
			return description;
		}



		public void setDescription(String description) {
			this.description = description;
		}



		public Date getDate() {
			return date;
		}



		public void setDate(Date date) {
			this.date = date;
		}



		public String getPoster() {
			return poster;
		}



		public void setPoster(String poster) {
			this.poster = poster;
		}



		public Film getFilm() {
			return film;
		}



		public void setFilm(Film film) {
			this.film = film;
		}



		public List<Seance> getSeances() {
			return seances;
		}



		public void setSeances(List<Seance> seances) {
			this.seances = seances;
		}



		public List<Gallerie> getGalleries() {
			return galleries;
		}



		public void setGalleries(List<Gallerie> galleries) {
			this.galleries = galleries;
		}



		public TypeEvent getTypeEvent() {
			return typeEvent;
		}



		public void setTypeEvent(TypeEvent typeEvent) {
			this.typeEvent = typeEvent;
		}





	    
}
