package com.cinema.model;


import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name="users")
@Proxy(lazy = false)
public class UserDao  extends AbstractModel<Long>{
	
	
	private String username;
	@JsonIgnore
	private String password;
	public String getUsername() {
		return username;
		
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	
	   
    
	
	

	
	
	
	

	

}
