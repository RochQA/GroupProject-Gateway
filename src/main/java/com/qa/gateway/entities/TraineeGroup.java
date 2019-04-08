package com.qa.gateway.entities;

import java.util.List;

import javax.persistence.Id;

public class TraineeGroup {
	
	@Id
	private Long id;
	
	private String name;
	
	private int size;
	
	private List<String> trainees;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<String> getTrainees() {
		return trainees;
	}

	public void setTrainees(List<String> trainees) {
		this.trainees = trainees;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
