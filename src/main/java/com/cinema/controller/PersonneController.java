package com.cinema.controller;

import com.cinema.model.Evenement;
import com.cinema.model.Film;
import com.cinema.model.Nationalite;
import com.cinema.model.Personne;
import com.cinema.model.Personne.roleEnum;
import com.cinema.repository.NationaliteRepository;
import com.cinema.service.NationaliteService;
import com.cinema.service.PersonneService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    private PersonneService personneService;

    @Autowired
    public void setPersonneService(PersonneService personneService) {
        this.personneService = personneService;
    }
    
    private NationaliteService nationaliteService;

    @Autowired
    public void setPersonneService(NationaliteService nationaliteService) {
        this.nationaliteService = nationaliteService;
    }
    
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

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonneById(@PathVariable("id") long id) {
      Optional<Personne> personne = personneService.findById(id);

      if (personne.isPresent()) {
        return new ResponseEntity<>(personne.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    
//    @PostMapping("/")
//    public ResponseEntity<Personne> createPersonne(@RequestBody Personne personne) {
//      try {
//    	 Personne newPersonne = personneService.save(personne);
//        return new ResponseEntity<>(newPersonne, HttpStatus.CREATED);
//      } catch (Exception e) {
//        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//      }
//    }
    
	@Autowired  ServletContext context;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/")
	 public ResponseEntity<Response> createPersonne(@RequestParam("file") MultipartFile file,
			 @RequestParam("personne") String personne) throws JsonParseException , JsonMappingException , Exception
	 {
	Personne per = new ObjectMapper().readValue(personne, Personne.class);
      boolean isExit = new java.io.File(context.getRealPath("/Images/personnes/")).exists();
      if (!isExit)
      {
      	new java.io.File(context.getRealPath("/Images/personnes/")).mkdir();
      }
      String filename = file.getOriginalFilename();
      String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
      System.out.println(newFileName);
      File serverFile = new File(context.getRealPath("/Images/personnes/"+File.separator+newFileName));
      try
      {
      	 FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
      	 
      }catch(Exception e) {
      	e.printStackTrace();
      }

     
      per.setPhoto(newFileName);
      Personne art = personneService.save(per);
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
    public ResponseEntity<Personne> updatePersonne(@PathVariable("id") long id,@RequestParam("file") MultipartFile file,
    		@RequestParam("personne") String personne) throws JsonParseException , JsonMappingException , Exception
	{
		Optional<Personne> currentPersonne = personneService.findById(id);
		Personne pers = new ObjectMapper().readValue(personne, Personne.class);
			
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		System.out.println(newFileName);
		File serverFile = new File(context.getRealPath("/Images/personnes/"+File.separator+newFileName));
		try
		{
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes()); 	 
		}catch(Exception e) {
			e.printStackTrace();
		}
		  
		Path path = Paths.get("src\\main\\webapp\\Images\\personnes\\"+currentPersonne.get().getPhoto());
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
		
      if (currentPersonne.isPresent()) {
        Personne updatePersonne = currentPersonne.get();
        updatePersonne.setNom(pers.getNom());
        updatePersonne.setPrenom(pers.getPrenom());
        updatePersonne.setDate_naissance(pers.getDate_naissance());
        updatePersonne.setType(pers.getType());
        updatePersonne.setPhoto(newFileName);
        updatePersonne.setNationalite(pers.getNationalite());
        
        return new ResponseEntity<>(personneService.save(updatePersonne), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePersonne(@PathVariable("id") long id) {
      try {
    	  personneService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    
    

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
    
    @GetMapping("/acteurs")
    public ResponseEntity<List<Personne>> getActeurs() {
      try {
    	  Personne personne = new Personne();
    	  List<Personne> personnes = personneService.findAllByType(personne.getType().acteur);
        if (!personnes.isEmpty()) {
        	return new ResponseEntity<>(personnes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


}
