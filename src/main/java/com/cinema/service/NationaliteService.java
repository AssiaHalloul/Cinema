package com.cinema.service;

import com.cinema.model.Nationalite;
import com.cinema.repository.NationaliteRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NationaliteService extends AbstractService<Nationalite, Long> {

    @Autowired
    private NationaliteRepository nationaliteRepository;

    @Override
    protected JpaRepository<Nationalite, Long> getRepository() {
        return nationaliteRepository;
    }

	public List<Nationalite> getListAll() {
		// TODO Auto-generated method stub
		
		return nationaliteRepository.findAll();
	}

	public Optional<Nationalite> findById(long id) {
		// TODO Auto-generated method stub
		return nationaliteRepository.findById(id);
	}

	public Nationalite findByLibelle(String libelle) {
		return nationaliteRepository.findByLibelle(libelle);
	}

}
