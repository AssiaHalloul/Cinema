package com.cinema.service;

import com.cinema.model.NewsLetter;
import com.cinema.repository.NewsLetterRepository;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsLetterService extends AbstractService<NewsLetter, Long> {

    @Autowired
    private NewsLetterRepository newsLetterRepository;

    @Override
    protected JpaRepository<NewsLetter, Long> getRepository() {
        return newsLetterRepository;
    }

	public List<NewsLetter> getListAll() {
		// TODO Auto-generated method stub
		
		return newsLetterRepository.findAll();
	}

	public Optional<NewsLetter> findById(long id) {
		// TODO Auto-generated method stub
		return newsLetterRepository.findById(id);
	}

	public NewsLetter findByLibelle(String email) {
		return newsLetterRepository.findByEmail(email);
	}

}
