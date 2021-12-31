package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Evenement;
import com.cinema.model.Gallerie;


@Repository
public interface EvenementRepository extends  JpaRepository<Evenement, Long> {
	List<Evenement>  findAllByFilmId(Long id);
}