package com.cinema.repository;

import com.cinema.model.Personne;
import com.cinema.model.Personne.roleEnum;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

	Personne findByNom(String nom);

	Personne findByType(roleEnum realisateur);

	List<Personne> findAllByType(roleEnum realisateur);

}
