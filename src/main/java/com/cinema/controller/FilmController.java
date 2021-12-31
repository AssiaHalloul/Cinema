package com.cinema.controller;

 import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
import com.cinema.model.Gallerie;
import com.cinema.model.Genre;
import com.cinema.service.FilmService;
import com.cinema.service.GallerieService;
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
	
	private GallerieService gallerieService;
	@Autowired
    public void setGallerieService(GallerieService gallerieService) {
        this.gallerieService = gallerieService;
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
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/filmByStatusANDGenre")
	public ResponseEntity<List<Film>> getFilmsByGenreANDStatus(@RequestParam("status") String status,@RequestParam("genre") String genre) {
	  try {
	    List<Film> films = new ArrayList<Film>();
	    if(status=="" && genre =="") {
	    	films = filmService.getListAll();
	    }else {
	    	films = filmService.getAllFilmsByStatusANDGenre(status, genre);
	    }
	    if (films.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(films, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/filmByDate")
	public ResponseEntity<List<Object>> AllFilmsByDate(@RequestParam("date") String date) {
	  try {
	    List<Object> films = new ArrayList<>();

	    films = filmService.getFilmsByDate(date);
	    
	    if (films.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(films, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
    
	@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value="/{id}",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Film> updateFilm(@PathVariable("id") long id,@RequestParam("file") MultipartFile file,
    		@RequestParam("film") String film) throws JsonParseException , JsonMappingException , Exception
{
		Optional<Film> currentFilm = filmService.findById(id);
		Film flm = new ObjectMapper().readValue(film, Film.class);
		
	  String filename = file.getOriginalFilename();
      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
      System.out.println(newFileName);
      File serverFile = new File(context.getRealPath("/Images/films/"+File.separator+newFileName));
      try
      {
      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
      	 
      }catch(Exception e) {
      	e.printStackTrace();
      }
      
      Path path = Paths.get("src\\main\\webapp\\Images\\films\\"+currentFilm.get().getPoster());
      System.out.println(path);
      String filePath = path.toAbsolutePath().toString();
      Path imagesPath = Paths.get(filePath);
      System.out.println("path "+filePath );

      try {
   
    	  Files.delete(imagesPath);
    	  System.out.println("File or directory deleted e successfully");  
      }
      
      catch(Exception e){
    	  System.out.println("eurrrrrrrrrr");
      }

      if (currentFilm.isPresent()) {
        Film updateFilm = currentFilm.get();
        updateFilm.setTitre(flm.getTitre());
        updateFilm.setDuree(flm.getDuree());
        updateFilm.setDescription(flm.getDescription());
        updateFilm.setAnnee(flm.getAnnee());
        updateFilm.setStatue(flm.getStatue());
        updateFilm.setPoster(newFileName);
        updateFilm.setTrailer(flm.getTrailer());
        updateFilm.setRealisateur(flm.getRealisateur());
        updateFilm.setActeurs(flm.getActeurs());
        updateFilm.setNationalite(flm.getNationalite());
        updateFilm.setGenre(flm.getGenre());
        
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
	
	
	@Autowired  ServletContext context;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping(value="/poster",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<Response> createPoster(@RequestParam("file") MultipartFile file,@RequestParam(value="files",required = false) MultipartFile[] files,
			 @RequestParam("film") String film) throws JsonParseException , JsonMappingException , Exception
	 {
		
		  System.out.println("Ok .............");
	      Film flm = new ObjectMapper().readValue(film, Film.class);
	      boolean isExit = new java.io.File(context.getRealPath("/Images/films/")).exists();
	      if (!isExit)
	      {
	      	new java.io.File(context.getRealPath("/Images/films/")).mkdir();
	      	System.out.println("mk dir.............");
	      }
	      String filename = file.getOriginalFilename();
	      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
	      System.out.println(newFileName);
	      File serverFile = new File(context.getRealPath("/Images/films/"+File.separator+newFileName));
	      try
	      {
	      	 System.out.println("Image");
	      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
	      	 
	      }catch(Exception e) {
	      	e.printStackTrace();
	      }
	      

      List<String> listphoto = new ArrayList();
      List photos = new ArrayList();

      boolean isExitgallerie = new java.io.File(context.getRealPath("/Images/films/gallerie/")).exists();
      if (!isExitgallerie)
      {
      	new java.io.File(context.getRealPath("/Images/films/gallerie/")).mkdir();
      	System.out.println("mk dir gallerie.............");
      }
      for(MultipartFile filegallerie: files) {
    	  System.out.println("Image singles files");
          String singlefilename = filegallerie.getOriginalFilename();
          String newsingleFileName = FilenameUtils.getBaseName(singlefilename)+"."+FilenameUtils.getExtension(singlefilename);
          System.out.println(newsingleFileName);
          File serverSingleFile = new File(context.getRealPath("/Images/films/gallerie/"+File.separator+newsingleFileName));
          try
          {
          	 System.out.println("Image singles");
          	 FileUtils.writeByteArrayToFile(serverSingleFile,filegallerie.getBytes());
          	 
          }catch(Exception e) {
          	e.printStackTrace();
          }
          
          photos.add(newsingleFileName);
      }
      
      System.out.println("phot size "+photos.size());
  

      flm.setPoster(newFileName);
      //flm.setGalleries(photos);
      Film art = filmService.save(flm);
      
      for(int i=0; i<photos.size();i++) {
    	  Gallerie gallerie= new Gallerie();
    	  gallerie.setFilm(flm);
    	  gallerie.setTitre("image "+ 1);
          System.out.println("photo name "+(String) photos.get(i));
    	  gallerie.setImage((String) photos.get(i));
    	  gallerieService.save(gallerie);
    	  System.out.println("done upload " +i);
      }
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
	@GetMapping(path="/imagePoster/{id}")
	 public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		 Film film   = filmService.findById(id).get();
		 return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+film.getPoster()));
	 }
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/filmByStatus")
	public ResponseEntity<List<Film>> getFilmsByStatus(@RequestParam("status") String status) {
	  try {
	    List<Film> films = new ArrayList<Film>();
	    films = filmService.getAllFilmsByStatus(status);
	    if (films.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(films, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
//	@GetMapping("/filmByStatusANDGenre")
//	public ResponseEntity<List<Film>> getFilmsByGenreANDStatus(@RequestParam("status") String status,@RequestParam("genre") String genre) {
//	  try {
//	    List<Film> films = new ArrayList<Film>();
//	    films = filmService.getAllFilmsByStatusANDGenre(status, genre);
//	    if (films.isEmpty()) {
//	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    }
//	    return new ResponseEntity<>(films, HttpStatus.OK);
//	  } catch (Exception e) {
//	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	  }
//	}

}