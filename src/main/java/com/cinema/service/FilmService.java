package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Film;
import com.cinema.model.Genre;
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
	
	public List<Film> getAllFilmsByStatusANDGenre(String status, String genre){
		return filmRepository.SearchStatueANDGenre(status, genre);
	}
	
	public List<Object> getFilmsByDate(String date) {
		// TODO Auto-generated method stub
		return filmRepository.getFilmsByDate(date);
	}
	
	public List<Film> getAllFilmsByStatus(String status){
		return filmRepository.findAllByStatue(status);
	}
	
//	public List<Film> getAllFilmsByStatusANDGenre(String status, String genre){
//		return filmRepository.findAllByStatueANDGenre(status, genre);
//	}

}