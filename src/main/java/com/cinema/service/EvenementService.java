package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Evenement;


import com.cinema.repository.EvenementRespository;


@Service
public class EvenementService extends AbstractService<Evenement, Long> {
	@Autowired
	private EvenementRespository evenementRepository;

	@Override
	protected JpaRepository<Evenement, Long> getRepository() {
		// TODO Auto-generated method stub
		return  evenementRepository;
	}

	public List<Evenement> getListAll() {
		// TODO Auto-generated method stub
		
		return evenementRepository.findAll();
	}

	public Optional<Evenement> findById(long id) {
		// TODO Auto-generated method stub
		return evenementRepository.findById(id);
	}

}

