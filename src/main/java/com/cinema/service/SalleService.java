package com.cinema.service;

import com.cinema.model.Salle;
import com.cinema.repository.SalleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SalleService extends AbstractService<Salle, Long> {

    @Autowired
    private SalleRepository salleRepository;

    @Override
    protected JpaRepository<Salle, Long> getRepository() {
        return salleRepository;
    }

	public List<Salle> getListAll() {
		// TODO Auto-generated method stub
		return salleRepository.findAll();
	}

	public Optional<Salle> findById(long id) {
		// TODO Auto-generated method stub
		return salleRepository.findById(id);
	}

	public Salle findByNum(Long numero) {
		// TODO Auto-generated method stub
		return salleRepository.findByNum(numero);
	}

}
