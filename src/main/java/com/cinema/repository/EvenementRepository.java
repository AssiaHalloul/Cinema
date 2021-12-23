package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Evenement;


@Repository
public interface EvenementRepository extends  JpaRepository<Evenement, Long> {

}