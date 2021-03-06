package com.cinema.repository;

import com.cinema.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {

	Salle findByNum(Long numero);

}
