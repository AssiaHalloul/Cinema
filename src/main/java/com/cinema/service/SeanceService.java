package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Seance;
import com.cinema.repository.SeanceRepository;
@Service
public class SeanceService extends AbstractService<Seance, Long> {

    @Autowired
    private SeanceRepository seanceRepository;

    @Override
    protected JpaRepository<Seance, Long> getRepository() {
        return seanceRepository;
    }

	public List<Seance> getListAll() {
		// TODO Auto-generated method stub
		return seanceRepository.findAll();
	}

	public Optional<Seance> findById(long id) {
		// TODO Auto-generated method stub
		return seanceRepository.findById(id);
	}
	
	public List<Object> getFilmSeances(long film){
		return seanceRepository.findAllByFilmId(film);
	}


}
