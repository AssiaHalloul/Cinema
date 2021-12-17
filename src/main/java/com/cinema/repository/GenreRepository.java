package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Genre;


@Repository
public interface GenreRepository extends  JpaRepository<Genre, Long> {

}
