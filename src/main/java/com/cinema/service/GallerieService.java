
package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import com.cinema.model.Gallerie;

import com.cinema.repository.GallerieRepository;


@Service
public class GallerieService extends AbstractService<Gallerie, Long> {
	@Autowired
	private GallerieRepository gallerieRepository;

	@Override
	protected JpaRepository<Gallerie, Long> getRepository() {
		// TODO Auto-generated method stub
		return  gallerieRepository;
	}

	public List<Gallerie> getListAll() {
		// TODO Auto-generated method stub
		return gallerieRepository.findAll();
	}
	
	public List<Gallerie> getListAllFilms(Long id) {
		// TODO Auto-generated method stub
		return gallerieRepository.findAllByFilmId(id);
	}
	
	public List<Gallerie> getListAllFilmsEvent(Long id) {
		// TODO Auto-generated method stub
		return gallerieRepository.findAllByEvenementId(id);
	}

	public Optional<Gallerie> findById(long id) {
		// TODO Auto-generated method stub
		return gallerieRepository.findById(id);
	}

}

