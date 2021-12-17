
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

import com.cinema.model.Evenement;
import com.cinema.service.EvenementService;


@Controller
@RequestMapping("/api/evenements")
public class EvenementController {
	private EvenementService evenementService;

    @Autowired
   
    public void setGenreService(EvenementService evenementService) {
        this.evenementService = evenementService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Evenement>> getAllGenres() {
      try {
        List<Evenement> evenements = new ArrayList<Evenement>();
        evenements = evenementService.getListAll();
        if (evenements.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(evenements, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evenement> getGenreById(@PathVariable("id") long id) {
      Optional<Evenement> evenement = evenementService.findById(id);

      if (evenement.isPresent()) {
        return new ResponseEntity<>(evenement.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Evenement> createGenre(@RequestBody Evenement evenement) {
      try {
    	  Evenement newFilm = evenementService.save(evenement);
        return new ResponseEntity<>(newFilm, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Evenement> updatEvenement(@PathVariable("id") long id, @RequestBody Evenement evenement) {
      Optional<Evenement> currentEvenement = evenementService.findById(id);

      if (currentEvenement.isPresent()) {
    	  Evenement updateEvenement= currentEvenement.get();
    	  updateEvenement.setDate(evenement.getDate());
    	  updateEvenement.setDescription(evenement.getDescription());
    	  updateEvenement.setDuree(evenement.getDuree());
    	  updateEvenement.setTitre(evenement.getTitre());
    	  updateEvenement.setPoster(evenement.getPoster());
    	  updateEvenement.setTypeEvent(evenement.getTypeEvent());
    	  updateEvenement.setFilm(evenement.getFilm());
       
        
        
        return new ResponseEntity<>(evenementService.save(updateEvenement), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteEvenement(@PathVariable("id") long id) {
      try {
    	  evenementService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}

