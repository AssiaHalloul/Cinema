package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Genre;
import com.cinema.model.Nationalite;
import com.cinema.repository.GenreRepository;


@Service
public class GenreService extends AbstractService<Genre, Long> {
	@Autowired
	private GenreRepository genreRepository;

	@Override
	protected JpaRepository<Genre, Long> getRepository() {
		// TODO Auto-generated method stub
		return  genreRepository;
	}

	public List<Genre> getListAll() {
		// TODO Auto-generated method stub
		
		return genreRepository.findAll();
	}

	public Optional<Genre> findById(long id) {
		// TODO Auto-generated method stub
		return genreRepository.findById(id);
	}

}
