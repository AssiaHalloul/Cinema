
package com.cinema.service;

import com.cinema.model.Personne;
import com.cinema.model.Personne.roleEnum;
import com.cinema.repository.PersonneRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonneService extends AbstractService<Personne, Long> {

    @Autowired
    private PersonneRepository personneRepository;

    @Override
    protected JpaRepository<Personne, Long> getRepository() {
        return personneRepository;
    }

	public List<Personne> getListAll() {
		// TODO Auto-generated method stub
		
		return personneRepository.findAll();
	}

	public Optional<Personne> findById(long id) {
		// TODO Auto-generated method stub
		return personneRepository.findById(id);
	}

	public List<Personne> findAllByType(roleEnum nom) {
		return personneRepository.findAllByType(nom);
	}

}
