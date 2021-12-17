package com.cinema.repository;

import com.cinema.model.NewsLetter;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {

	NewsLetter findByEmail(String email);

}
