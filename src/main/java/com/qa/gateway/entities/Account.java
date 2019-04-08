package com.qa.gateway.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {
	
	@Id
	private Long id;

	private String email;
	
	private String password;
	
	private Trainer trainer;
	
	private Boolean admin;
	
	
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}


	
	
	
}
