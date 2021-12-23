package com.cinema.controller;

import com.cinema.model.Nationalite;
import com.cinema.service.NationaliteService;

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


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/nationalites")
public class NationaliteController {

    private NationaliteService nationaliteService;

    @Autowired
    public void setNationaliteService(NationaliteService nationaliteService) {
        this.nationaliteService = nationaliteService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Nationalite>> getAllNationalites() {
      try {
        List<Nationalite> nationalites = new ArrayList<Nationalite>();
        nationalites = nationaliteService.getListAll();
        if (nationalites.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(nationalites, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nationalite> getNationaliteById(@PathVariable("id") long id) {
      Optional<Nationalite> nationalite = nationaliteService.findById(id);

      if (nationalite.isPresent()) {
        return new ResponseEntity<>(nationalite.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/")
    public ResponseEntity<Nationalite> createNationalite(@RequestBody Nationalite nationalite) {
      try {
    	  Nationalite newNationalite = nationaliteService.save(nationalite);
        return new ResponseEntity<>(newNationalite, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nationalite> updateNationalite(@PathVariable("id") long id, @RequestBody Nationalite nationalite) {
      Optional<Nationalite> currentNationalite = nationaliteService.findById(id);

      if (currentNationalite.isPresent()) {
        Nationalite updateNationalite = currentNationalite.get();
        updateNationalite.setLibelle(nationalite.getLibelle());
        return new ResponseEntity<>(nationaliteService.save(updateNationalite), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNationalite(@PathVariable("id") long id) {
      try {
    	nationaliteService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/rechercher")
    public ResponseEntity<Nationalite> getNationaliteByLibelle(@RequestParam(value = "libelle") String text) {
      try {
        Nationalite nationalite = nationaliteService.findByLibelle(text);
        if (nationalite == null) {
          return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(nationalite, HttpStatus.NO_CONTENT);
        
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    
   
}
