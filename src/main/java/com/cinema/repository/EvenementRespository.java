package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.model.Evenement;


public interface EvenementRespository extends  JpaRepository<Evenement, Long> {

}