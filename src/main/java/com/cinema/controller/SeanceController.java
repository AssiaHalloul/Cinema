package com.cinema.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.model.Film;
import com.cinema.model.Seance;
import com.cinema.service.FilmService;
import com.cinema.service.SeanceService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/seances")
public class SeanceController {
	private  SeanceService seanceService;
	@Autowired
    public void setSeanceService(SeanceService seanceService) {
        this.seanceService = seanceService;
    }
	
	@GetMapping("/")
    public ResponseEntity<List<Seance>> getAllSeances() {
      try {
        List<Seance> seances = new ArrayList<Seance>();
        seances = seanceService.getListAll();
        if (seances.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(seances, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable("id") long id) {
      Optional<Seance> seance = seanceService.findById(id);

      if (seance.isPresent()) {
        return new ResponseEntity<>(seance.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
	
	@PostMapping("/")
    public ResponseEntity<Seance> createSeance(@RequestBody Seance seance) {
      try {
    	  
    	  Seance newSeance = seanceService.save(seance);
        return new ResponseEntity<>(newSeance, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/filmSeances")
	public ResponseEntity<List<Object>> AllFilmSeances(@RequestParam("film") long film) {
	  try {
	    List<Object> seances = new ArrayList<>();

	    seances = seanceService.getFilmSeances(film);
	    
	    if (seances.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(seances, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Seance> updateSeance(@PathVariable("id") long id, @RequestBody Seance seance) {
      Optional<Seance> currentSeance = seanceService.findById(id);

      if (currentSeance.isPresent()) {
        Seance updateSeance = currentSeance.get();
        updateSeance.setDate(seance.getDate());
        updateSeance.setFilm(seance.getFilm());
        updateSeance.setEvenement(seance.getEvenement());
        updateSeance.setSalle(seance.getSalle());
        
        
        return new ResponseEntity<>(seanceService.save(updateSeance), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSeance(@PathVariable("id") long id) {
      try {
    	seanceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	    
	
}
