

package com.cinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Film;
import com.cinema.model.Gallerie;



@Repository
public interface GallerieRepository extends  JpaRepository<Gallerie, Long> {
	List<Gallerie>  findAllByFilmId(Long id);
	List<Gallerie>  findAllByEvenementId(Long id);
}
