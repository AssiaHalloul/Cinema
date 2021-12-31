package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cinema.model.Film;
import com.cinema.model.Genre;


@Repository
public interface FilmRepository extends  JpaRepository<Film, Long> {
	
	List<Film>  findAllByStatue(String status);
//	List<Film>  findAllByStatueANDGenre(String status,String genre);
	@Query("SELECT f FROM Film f JOIN f.genre g WHERE f.statue=:status or g.nom=:genre")
	List<Film>  SearchStatueANDGenre(String status,String genre);
	
	@Query("SELECT DISTINCT f FROM Film f JOIN f.seances s WHERE f.id=s.film.id and s.film!=null and DATE_FORMAT(s.date,'%Y-%m-%d')=:date")
	List<Object> getFilmsByDate(String date);
}
