package com.cinema.controller;

 import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

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

import com.cinema.model.Film;
import com.cinema.service.FilmService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/films")
public class FilmController {
	
	private FilmService filmService;
	@Autowired
    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/")
    public ResponseEntity<List<Film>> getAllFilms() {
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
	
	@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable("id") long id) {
      Optional<Film> film = filmService.findById(id);

      if (film.isPresent()) {
        return new ResponseEntity<>(film.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
	
//	@CrossOrigin(origins = "http://localhost:4200")
//    @PostMapping("/")
//    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
//      try {
//    	  Film newFilm = filmService.save(film);
//        return new ResponseEntity<>(newFilm, HttpStatus.CREATED);
//      } catch (Exception e) {
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
    
	@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable("id") long id, @RequestBody Film film) {
      Optional<Film> currentFilm = filmService.findById(id);

      if (currentFilm.isPresent()) {
        Film updateFilm = currentFilm.get();
        updateFilm.setTitre(film.getTitre());
        updateFilm.setDuree(film.getDuree());
        updateFilm.setDescription(film.getDescription());
        updateFilm.setAnnee(film.getAnnee());
        updateFilm.setStatue(film.getStatue());
        updateFilm.setPoster(film.getPoster());
        updateFilm.setTrailer(film.getTrailer());
        updateFilm.setRealisateur(film.getRealisateur());
        updateFilm.setActeurs(film.getActeurs());
        updateFilm.setNationalite(film.getNationalite());
        updateFilm.setGenre(film.getGenre());
        
        return new ResponseEntity<>(filmService.save(updateFilm), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
	@CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteFilm(@PathVariable("id") long id) {
      try {
    	filmService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping(path="/imagePoster/{id}")
	 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		 Film film   = filmService.findById(id).get();
		 System.out.println(Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+film.getPoster())));
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+film.getPoster()));
	 }
	
	
	
	
@Autowired  ServletContext context;

@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/poster")
	 public ResponseEntity<Response> createPoster (@RequestParam("file") MultipartFile file,
			 @RequestParam("film") String film) throws JsonParseException , JsonMappingException , Exception
	 {
		 System.out.println("Ok .............");
     Film flm = new ObjectMapper().readValue(film, Film.class);
      boolean isExit = new java.io.File(context.getRealPath("/Images/")).exists();
      if (!isExit)
      {
      	new java.io.File(context.getRealPath("/Images/")).mkdir();
      	System.out.println("mk dir.............");
      }
      String filename = file.getOriginalFilename();
      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
      System.out.println(newFileName);
      File serverFile = new File(context.getRealPath("/Images/"+File.separator+newFileName));
      try
      {
      	System.out.println("Image");
      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
      	 
      }catch(Exception e) {
      	e.printStackTrace();
      }

     
      flm.setPoster(newFileName);
      Film art = filmService.save(flm);
      if (art != null)
      {

      	return new ResponseEntity<Response>(HttpStatus.OK);
      }
      else
      {
      	return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);	
      }
	 }
	
	

	
	
	
	
	
	
	
	
	

}
