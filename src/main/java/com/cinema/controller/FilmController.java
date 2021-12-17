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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.model.Film;
import com.cinema.service.FilmService;


@Controller
@RequestMapping("/api/films")
public class FilmController {
	private FilmService filmService;

    @Autowired
   
    public void setGenreService(FilmService filmService) {
        this.filmService = filmService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Film>> getAllGenres() {
      try {
        List<Film> films = new ArrayList<Film>();
        films = filmService.getListAll();
        if (films.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(films, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getGenreById(@PathVariable("id") long id) {
      Optional<Film> film = filmService.findById(id);

      if (film.isPresent()) {
        return new ResponseEntity<>(film.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Film> createGenre(@RequestBody Film film) {
      try {
    	  Film newFilm = filmService.save(film);
        return new ResponseEntity<>(newFilm, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Film> updateGenre(@PathVariable("id") long id, @RequestBody Film film) {
      Optional<Film> currentFilm = filmService.findById(id);

      if (currentFilm.isPresent()) {
        Film updateFilm = currentFilm.get();
        updateFilm.setTitre(film.getTitre());
        updateFilm.setAnnee(film.getAnnee());
        updateFilm.setDescription(film.getDescription());
        updateFilm.setDuree(film.getDuree());
        updateFilm.setPoster(film.getPoster());
        updateFilm.setTrailer(film.getTrailer());
        updateFilm.setNationalite(film.getNationalite());
        updateFilm.setGenre(film.getGenre());
        updateFilm.setActeurs(film.getActeurs());
        updateFilm.setRealisateur(film.getRealisateur());
        updateFilm.setStatue(film.getStatue());
        
        
        return new ResponseEntity<>(filmService.save(updateFilm), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable("id") long id) {
      try {
    	  filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
