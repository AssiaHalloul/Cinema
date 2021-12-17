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

import com.cinema.model.Genre;
import com.cinema.model.TypeEvent;
import com.cinema.service.TypeEventService;

@Controller
@RequestMapping("/api/typeEvents")
public class TypeEventController {
	private TypeEventService typeEventService;

    @Autowired
   
    public void setTypeEventService(TypeEventService typeEventService) {
        this.typeEventService = typeEventService;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<TypeEvent>> getAllTypesEvent() {
      try {
        List<TypeEvent> typeEvents = new ArrayList<TypeEvent>();
        typeEvents = typeEventService.getListAll();
        if (typeEvents.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(typeEvents, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeEvent> getTypeEventById(@PathVariable("id") long id) {
      Optional<TypeEvent> typeEvent = typeEventService.findById(id);

      if (typeEvent.isPresent()) {
        return new ResponseEntity<>(typeEvent.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
    @PostMapping("/add")
    public ResponseEntity<TypeEvent> createTypeEvent(@RequestBody TypeEvent typeEvent) {
      try {
    	  TypeEvent newTypeEvent = typeEventService.save(typeEvent);
        return new ResponseEntity<>(newTypeEvent, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TypeEvent> updateTypeEvent(@PathVariable("id") long id, @RequestBody TypeEvent typeEvent) {
      Optional<TypeEvent> currentTypeEvent = typeEventService.findById(id);

      if (currentTypeEvent.isPresent()) {
        TypeEvent updateTypeEvent = currentTypeEvent.get();
        updateTypeEvent.setType_event(typeEvent.getType_event());
        return new ResponseEntity<>(typeEventService.save(updateTypeEvent), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteTypeEvent(@PathVariable("id") long id) {
      try {
    	  typeEventService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}


