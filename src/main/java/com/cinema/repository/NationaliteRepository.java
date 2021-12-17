package com.cinema.repository;

import com.cinema.model.Nationalite;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {

	Nationalite findByLibelle(String libelle);

}
