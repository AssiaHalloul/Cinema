package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Film;
import com.cinema.model.Seance;

@Repository
public interface SeanceRepository extends  JpaRepository<Seance, Long> {
	List<Object>  findAllByFilmId(long film);

}