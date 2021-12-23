
package com.cinema.controller;

import com.cinema.model.Personne;
import com.cinema.service.PersonneService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private PersonneService personneService;

    @Autowired
    public void setPersonneService(PersonneService personneService) {
        this.personneService = personneService;
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<Personne>> getAllPersonnes() {
      try {
        List<Personne> personnes = new ArrayList<Personne>();
        personnes = personneService.getListAll();
        if (personnes.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(personnes, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable("id") long id) {
    Optional<Personne> personne = personneService.findById(id);

    if (personne.isPresent()) {
    return new ResponseEntity<>(personne.get(), HttpStatus.OK);
    } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }
    
    

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/rechercher")
    public ResponseEntity<List<Personne>> getRealisateurs() {
    try {
    Personne personne = new Personne();
    List<Personne> personnes = personneService.findAllByType(personne.getType().realisateur);
    if (!personnes.isEmpty()) {
    return new ResponseEntity<>(personnes, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
        
    
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
      try {
    	  Personne newPersonne = personneService.save(personne);
        return new ResponseEntity<>(newPersonne, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable("id") long id, @RequestBody Personne personne) {
      Optional<Personne> currentPersonne = personneService.findById(id);

      if (currentPersonne.isPresent()) {
        Personne updatePersonne = currentPersonne.get();
        updatePersonne.setNom(personne.getNom());
        updatePersonne.setPrenom(personne.getPrenom());
        updatePersonne.setDate_naissance(personne.getDate_naissance());
        updatePersonne.setType(personne.getType());
        updatePersonne.setNationalite(personne.getNationalite());
        
        
        return new ResponseEntity<>(personneService.save(updatePersonne), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePersonne(@PathVariable("id") long id) {
      try {
    	  personneService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
//
//    @GetMapping("/rechercher")
//    public ResponseEntity<Nationalite> getNationaliteByLibelle() {
//      try {
//        Nationalite nationalite = nationaliteService.findByLibelle("Francais");
//        if (nationalite == null) {
//          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(nationalite, HttpStatus.OK);
//        
//      } catch (Exception e) {
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }


}
