

package com.cinema.controller;

import com.cinema.model.Nationalite;
import com.cinema.model.NewsLetter;
import com.cinema.service.NewsLetterService;

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
@RequestMapping("/api/newsLetters")
public class NewsLetterController {

    private NewsLetterService newsLetterService;

    @Autowired
    public void setNewsLetterService(NewsLetterService newsLetterService) {
        this.newsLetterService = newsLetterService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<NewsLetter>> getAllNewsLetters() {
      try {
        List<NewsLetter> newsLetters = new ArrayList<NewsLetter>();
        newsLetters = newsLetterService.getListAll();
        if (newsLetters.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(newsLetters, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsLetter> getNationaliteById(@PathVariable("id") long id) {
      Optional<NewsLetter> newsLetter = newsLetterService.findById(id);

      if (newsLetter.isPresent()) {
        return new ResponseEntity<>(newsLetter.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/")
    public ResponseEntity<NewsLetter> createNewsLetter(@RequestBody NewsLetter newsLetter) {
      try {
    	  NewsLetter newNewsLetter = newsLetterService.save(newsLetter);
        return new ResponseEntity<>(newNewsLetter, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<NewsLetter> updatenewNewsLetter(@PathVariable("id") long id, @RequestBody NewsLetter newNewsLetter) {
      Optional<NewsLetter> currentNewsLetter = newsLetterService.findById(id);

      if (currentNewsLetter.isPresent()) {
    	  NewsLetter updateNewsLetter = currentNewsLetter.get();
        updateNewsLetter.setEmail(newNewsLetter.getEmail());
        return new ResponseEntity<>(newsLetterService.save(updateNewsLetter), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteNationalite(@PathVariable("id") long id) {
      try {
    	  newsLetterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
//
//    @GetMapping("/rechercher")
//    public ResponseEntity<NewsLetter> getNationaliteByLibelle() {
//      try {
//    	  NewsLetter newsLetter = newsLetterService.findByL("Francais");
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

