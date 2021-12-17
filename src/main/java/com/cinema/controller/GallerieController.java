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

import com.cinema.model.Gallerie;
import com.cinema.service.GallerieService;


@Controller
@RequestMapping("/api/galleries")
public class GallerieController {
	private GallerieService gallerieService;

    @Autowired
   
    public void setGenreService(GallerieService gallerieService) {
        this.gallerieService = gallerieService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Gallerie>> getAllCalleries() {
      try {
        List<Gallerie> galleries = new ArrayList<Gallerie>();
        galleries = gallerieService.getListAll();
        if (galleries.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(galleries, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gallerie> getgalleriesById(@PathVariable("id") long id) {
      Optional<Gallerie> gallerie = gallerieService.findById(id);

      if (gallerie.isPresent()) {
        return new ResponseEntity<>(gallerie.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Gallerie> creategallerie(@RequestBody Gallerie gallerie) {
      try {
    	  Gallerie newGallerie = gallerieService.save(gallerie);
        return new ResponseEntity<>(newGallerie, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Gallerie> updateGallerie(@PathVariable("id") long id, @RequestBody Gallerie gallerie) {
      Optional<Gallerie> currentGallerie = gallerieService.findById(id);

      if (currentGallerie.isPresent()) {
        Gallerie updateGallerie = currentGallerie.get();
        updateGallerie.setTitre(gallerie.getTitre());
        updateGallerie.setImage(gallerie.getImage());
        updateGallerie.setFilm(gallerie.getFilm());
        updateGallerie.setEvenement(gallerie.getEvenement());
        
      
        
        
        return new ResponseEntity<>(gallerieService.save(updateGallerie), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteGallerie(@PathVariable("id") long id) {
      try {
    	  gallerieService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
