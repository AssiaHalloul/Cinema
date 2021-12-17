

package com.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.model.Gallerie;



@Repository
public interface GallerieRepository extends  JpaRepository<Gallerie, Long> {

}
