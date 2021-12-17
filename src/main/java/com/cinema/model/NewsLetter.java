package com.cinema.model;


import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Proxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the categorie database table.
 * 
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="newLetters")
@Proxy(lazy = false)
public class NewsLetter  extends AbstractModel<Long>{
	@NotEmpty(message = "Email field should not be empty")
	@Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
	@Column(nullable = false, unique = true)
	    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	 

	
}
