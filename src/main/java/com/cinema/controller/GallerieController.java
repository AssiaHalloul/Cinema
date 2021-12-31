package com.cinema.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cinema.model.Film;
import com.cinema.model.Gallerie;
import com.cinema.service.GallerieService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/galleries")
public class GallerieController {
	private GallerieService gallerieService;

    @Autowired
   
    public void setGenreService(GallerieService gallerieService) {
        this.gallerieService = gallerieService;
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
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
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/filmsImages/{id}")
    public ResponseEntity<List<Gallerie>> getAllImagesByFilm(@PathVariable("id")Long id) {
      try {
        List<Gallerie> galleries = new ArrayList<Gallerie>();
        galleries = gallerieService.getListAllFilms(id);
        if (galleries.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(galleries, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/EventFilmImages/{id}")
    public ResponseEntity<List<Gallerie>> getAllImagesByFilmEvent(@PathVariable("id")Long id) {
      try {
        List<Gallerie> galleries = new ArrayList<Gallerie>();
        galleries = gallerieService.getListAllFilmsEvent(id);
        if (galleries.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(galleries, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{id}")
    public ResponseEntity<Gallerie> getgalleriesById(@PathVariable("id") long id) {
      Optional<Gallerie> gallerie = gallerieService.findById(id);

      if (gallerie.isPresent()) {
        return new ResponseEntity<>(gallerie.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
//    @CrossOrigin(origins = "http://localhost:4200")
//    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Gallerie> creategallerie(@RequestBody Gallerie gallerie,@RequestParam("files") MultipartFile files) {
//      try {
////    	  Gallerie newGallerie = gallerieService.save(gallerie);
//    	  System.err.println("files" +files);
//          return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//
////        return new ResponseEntity<>(newGallerie, HttpStatus.CREATED);
//      } catch (Exception e) {
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
    
    

//@CrossOrigin(origins = "http://localhost:4200")
//	@PostMapping("/")
//	 public ResponseEntity<Response> createPoster (@RequestParam("files") MultipartFile[] files,
//			 @RequestParam("gallerie") String gallerie) throws JsonParseException , JsonMappingException , Exception
//	 {
//		 System.out.println("Ok .............");
//     Gallerie glr = new ObjectMapper().readValue(gallerie, Gallerie.class);
//      boolean isExit = new java.io.File(context.getRealPath("/Galleries/")).exists();
//      if (!isExit)
//      {
//      	new java.io.File(context.getRealPath("/Galleries/")).mkdir();
//      	System.out.println("mk dir.............");
//      }
//      String filename = file.getOriginalFilename();
//      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
//      System.out.println(newFileName);
//      File serverFile = new File(context.getRealPath("/Galleries/"+File.separator+newFileName));
//      try
//      {
//      	System.out.println("Image");
//      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
//      	 
//      }catch(Exception e) {
//      	e.printStackTrace();
//      }
//
//     
//      glr.setImage(newFileName);
//      Gallerie art = gallerieService.save(glr);
//      if (art != null)
//      {
//
//      	return new ResponseEntity<Response>(HttpStatus.OK);
//      }
//      else
//      {
//      	return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);	
//      }
//	 }
	

@Autowired ServletContext context;
@CrossOrigin(origins = "http://localhost:4200")
@PostMapping("/")
 public ResponseEntity<Response> createPoster (@RequestParam("files") MultipartFile[] files,
		 @RequestParam("gallerie") String gallerie) throws JsonParseException , JsonMappingException , Exception
 {
	
//	  System.out.println("Ok .............");
//      boolean isExit = new java.io.File(context.getRealPath("/Images/films/")).exists();
//      if (!isExit)
//      {
//      	new java.io.File(context.getRealPath("/Images/films/")).mkdir();
//      	System.out.println("mk dir.............");
//      }
//      String filename = file.getOriginalFilename();
//      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
//      System.out.println(newFileName);
//      File serverFile = new File(context.getRealPath("/Images/films/"+File.separator+newFileName));
//      try
//      {
//      	 System.out.println("Image");
//      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
//      	 
//      }catch(Exception e) {
//      	e.printStackTrace();
//      }
      

  List photos = new ArrayList();

  boolean isExitgallerie = new java.io.File(context.getRealPath("/Images/films/gallerie/")).exists();
  if (!isExitgallerie)
  {
  	new java.io.File(context.getRealPath("/Images/films/gallerie/")).mkdir();
  	System.out.println("mk dir gallerie.............");
  }
  for(MultipartFile filegallerie: files) {
      Gallerie glr = new ObjectMapper().readValue(gallerie, Gallerie.class);
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
	
      glr.setImage(newsingleFileName);
    
	  Gallerie gallerie1 =gallerieService.save(glr);
	  photos.add(gallerie1);
  }
  
  System.out.println("phot size "+photos.size());

//
//  glr.setImage(newFileName);
  //flm.setGalleries(photos);
  
  
  if (!photos.isEmpty())
  {

  	return new ResponseEntity<Response>(HttpStatus.OK);
  }
  else
  {
  	return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);	
  }
}







    
    
    
    
//    @CrossOrigin(origins = "http://localhost:4200")
//    @PutMapping("/{id}")
//    public ResponseEntity<Gallerie> updateGallerie(@PathVariable("id") long id, @RequestBody Gallerie gallerie) {
//      Optional<Gallerie> currentGallerie = gallerieService.findById(id);
//
//      if (currentGallerie.isPresent()) {
//        Gallerie updateGallerie = currentGallerie.get();
//        updateGallerie.setTitre(gallerie.getTitre());
//        updateGallerie.setImage(gallerie.getImage());
//        updateGallerie.setFilm(gallerie.getFilm());
//        updateGallerie.setEvenement(gallerie.getEvenement());
//        
//      
//        
//        
//        return new ResponseEntity<>(gallerieService.save(updateGallerie), HttpStatus.OK);
//      } else {
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//      }
//    }



@CrossOrigin(origins = "http://localhost:4200")
@PutMapping("/{id}")
public ResponseEntity<Gallerie> updateGallerie(@PathVariable("id") long id, @RequestParam("gallerie") String gallerie,  @RequestParam("file") MultipartFile file) throws IOException {
 

	 Gallerie glr = new ObjectMapper().readValue(gallerie, Gallerie.class);
	 Optional<Gallerie> currentGallerie = gallerieService.findById(id);
	 
  Gallerie gallerieold= currentGallerie.get();
  System.out.println("cureent :"+ gallerieold.getId());

 
  if (currentGallerie.isPresent()) {
	  Gallerie updateGallerie = currentGallerie.get();
      updateGallerie.setTitre(glr.getTitre());
     
      updateGallerie.setFilm(glr.getFilm());
      updateGallerie.setEvenement(glr.getEvenement());
      Path path = Paths.get("src/main/webapp/Images/films/gallerie/"+gallerieold.getImage());
      System.out.println(path);
      String filePath = path.toAbsolutePath().toString();
      
      Path imagesPath = Paths.get(filePath);

      try {
   
    	  Files.delete(imagesPath);
	    System.out.println("File or directory deleted e successfully");  
      }
      
      catch(Exception e){
    	  System.out.println("eurrrrrrrrrr");
      }
      String filename = file.getOriginalFilename();
      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
      System.out.println(newFileName);
      File serverFile = new File(context.getRealPath("/Images/films/gallerie/"+File.separator+newFileName));
      try
      {
      	System.out.println("Image");
      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
      	 
      }catch(Exception e) {
      	e.printStackTrace();
      }

     
      updateGallerie.setImage(newFileName);

    return new ResponseEntity<>(gallerieService.save(updateGallerie), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}








    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGallerie(@PathVariable("id") long id) {
      try {
    	  gallerieService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    
}