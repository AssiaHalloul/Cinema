package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Film;
import com.cinema.repository.FilmRepository;
@Service
public class FilmService extends AbstractService<Film, Long> {
	@Autowired
	private FilmRepository filmRepository;

	@Override
	protected JpaRepository<Film, Long> getRepository() {
		// TODO Auto-generated method stub
		return  filmRepository;
	}

	public List<Film> getListAll() {
		// TODO Auto-generated method stub
		
		return filmRepository.findAll();
	}

	public Optional<Film> findById(long id) {
		// TODO Auto-generated method stub
		return filmRepository.findById(id);
	}

}