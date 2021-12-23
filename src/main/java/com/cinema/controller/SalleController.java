package com.cinema.controller;

import com.cinema.model.Salle;
import com.cinema.model.Salle;
import com.cinema.service.SalleService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/salles")
public class SalleController {

    private SalleService salleService;

    @Autowired
    public void setSalleService(SalleService salleService) {
        this.salleService = salleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Salle>> getAllSalles() {
      try {
        List<Salle> salles = new ArrayList<Salle>();
        salles = salleService.getListAll();
        if (salles.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salles, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable("id") long id) {
      Optional<Salle> salle = salleService.findById(id);

      if (salle.isPresent()) {
        return new ResponseEntity<>(salle.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
      try {
    	  Salle newSalle = salleService.save(salle);
        return new ResponseEntity<>(newSalle, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Salle> updateSalle(@PathVariable("id") long id, @RequestBody Salle salle) {
      Optional<Salle> currentSalle = salleService.findById(id);

      if (currentSalle.isPresent()) {
        Salle updateSalle = currentSalle.get();
        updateSalle.setNum(salle.getNum());
        updateSalle.setNombre_places(salle.getNombre_places());
        return new ResponseEntity<>(salleService.save(updateSalle), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSalle(@PathVariable("id") long id) {
      try {
    	salleService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/rechercher")
    public ResponseEntity<Salle> getSalleByNum(@RequestParam(value= "num") Long numero) {
      try {
        Salle salle = salleService.findByNum(numero);
        if (salle == null) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(salle, HttpStatus.OK);
        
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
