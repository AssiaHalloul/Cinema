package com.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.cinema.model.Genre;
import com.cinema.model.TypeEvent;
import com.cinema.repository.TypeEventRepository;

@Service
public class TypeEventService extends AbstractService<TypeEvent, Long> {

	@Autowired
	private TypeEventRepository typeEventRepository;
	@Override
	protected JpaRepository<TypeEvent, Long> getRepository() {
		// TODO Auto-generated method stub
		return typeEventRepository;
	}
	public List<TypeEvent> getListAll() {
		// TODO Auto-generated method stub
		
		return typeEventRepository.findAll();
	}

	public Optional<TypeEvent> findById(long id) {
		// TODO Auto-generated method stub
		return typeEventRepository.findById(id);
	}
}
