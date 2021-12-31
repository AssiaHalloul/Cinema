package com.cinema.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.model.Genre;
import com.cinema.model.Nationalite;
import com.cinema.service.GenreService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/genres")
public class GenreController {
	private GenreService genreService;

    @Autowired
   
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Genre>> getAllGenres() {
      try {
        List<Genre> genres = new ArrayList<Genre>();
        genres = genreService.getListAll();
        if (genres.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(genres, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") long id) {
      Optional<Genre> genre = genreService.findById(id);

      if (genre.isPresent()) {
        return new ResponseEntity<>(genre.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
      try {
    	  Genre newGenre = genreService.save(genre);
        return new ResponseEntity<>(newGenre, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") long id, @RequestBody Genre genre) {
      Optional<Genre> currentGenre = genreService.findById(id);

      if (currentGenre.isPresent()) {
        Genre updateGenre = currentGenre.get();
        updateGenre.setNom(genre.getNom());
        return new ResponseEntity<>(genreService.save(updateGenre), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGenre(@PathVariable("id") long id) {
      try {
    	genreService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
