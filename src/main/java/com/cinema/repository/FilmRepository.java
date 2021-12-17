
package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Film;


@Repository
public interface FilmRepository extends  JpaRepository<Film, Long> {

}
