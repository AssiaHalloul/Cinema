package com.cinema.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.cinema.model.Evenement;
import com.cinema.model.Film;
import com.cinema.model.Gallerie;
import com.cinema.model.Personne;
import com.cinema.service.EvenementService;
import com.cinema.service.FilmService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/evenements")
public class EvenementController {
	private EvenementService evenementService;
	@Autowired
    public void setEvenementService(EvenementService evenementService) {
        this.evenementService = evenementService;
    }
	
	@GetMapping("/")
    public ResponseEntity<List<Evenement>> getAllEvenements() {
      try {
        List<Evenement> events = new ArrayList<Evenement>();
        events = evenementService.getListAll();
        if (events.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/filmsEvent/{id}")
    public ResponseEntity<List<Evenement>> getAllImagesByFilm(@PathVariable("id")Long id) {
      try {
        List<Evenement> galleries = new ArrayList<Evenement>();
        galleries = evenementService.getListAllEventFilms(id);
        if (galleries.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(galleries, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Evenement> getEventById(@PathVariable("id") long id) {
      Optional<Evenement> event = evenementService.findById(id);

      if (event.isPresent()) {
        return new ResponseEntity<>(event.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
	
//	@PostMapping("/")
//    public ResponseEntity<Evenement> createEvent(@RequestBody Evenement evenement) {
//      try {
//    	  Evenement newEvent = evenementService.save(evenement);
//        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
//      } catch (Exception e) {
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
	
	@Autowired  ServletContext context;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/")
	 public ResponseEntity<Response> createEvent(@RequestParam("file") MultipartFile file,
			 @RequestParam("evenement") String evenement) throws JsonParseException , JsonMappingException , Exception
	 {
	Evenement event = new ObjectMapper().readValue(evenement, Evenement.class);
      boolean isExit = new java.io.File(context.getRealPath("/Images/evenements/")).exists();
      if (!isExit)
      {
      	new java.io.File(context.getRealPath("/Images/evenements/")).mkdir();
      }
      String filename = file.getOriginalFilename();
      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
      System.out.println(newFileName);
      File serverFile = new File(context.getRealPath("/Images/evenements/"+File.separator+newFileName));
      try
      {
      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
      	 
      }catch(Exception e) {
      	e.printStackTrace();
      }

     
      event.setPoster(newFileName);
      Evenement art = evenementService.save(event);
      if (art != null)
      {

      	return new ResponseEntity<Response>(HttpStatus.OK);
      }
      else
      {
      	return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);	
      }
	 }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping(value="/{id}",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Evenement> updateEvent(@PathVariable("id") long id,@RequestParam("file") MultipartFile file,
    		@RequestParam("evenement") String evenement) throws JsonParseException , JsonMappingException , Exception
	{
		Optional<Evenement> currentEvent = evenementService.findById(id);
		Evenement event = new ObjectMapper().readValue(evenement, Evenement.class);
			
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		System.out.println(newFileName);
		File serverFile = new File(context.getRealPath("/Images/evenements/"+File.separator+newFileName));
		try
		{
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes()); 	 
		}catch(Exception e) {
			e.printStackTrace();
		}
		  
		Path path = Paths.get("src\\main\\webapp\\Images\\evenements\\"+currentEvent.get().getPoster());
		System.out.println(path);
		String filePath = path.toAbsolutePath().toString();
		Path imagesPath = Paths.get(filePath);
		System.out.println("path "+filePath );

		try {   
			Files.delete(imagesPath);
			System.out.println("File or directory deleted e successfully");  
		}catch(Exception e){
			System.out.println("impossible to delete the file");
		}
		
      if (currentEvent.isPresent()) {
        Evenement updateEvent = currentEvent.get();
        updateEvent.setTitre(event.getTitre());
        updateEvent.setDuree(event.getDuree());
        updateEvent.setDescription(event.getDescription());
//        updateEvent.setDate(event.getDate());
        updateEvent.setPoster(newFileName);
        updateEvent.setFilm(event.getFilm());
        updateEvent.setTypeEvent(event.getTypeEvent());
      return new ResponseEntity<>(evenementService.save(updateEvent), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable("id") long id) {
      try {
    	evenementService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    

}
