package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.TypeEvent;


@Repository
public interface TypeEventRepository   extends  JpaRepository<TypeEvent, Long> {

}

