package com.qa.gateway.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plan {
	
	@Id
	private Long Id;
	
	private String topic;
	
	private String trainerName; 
	
	private String traineeGroup;
	
	private Date startDate;
	
	private Date endDate;
	
	private String room;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getTraineeGroup() {
		return traineeGroup;
	}

	public void setTraineeGroup(String traineeGroup) {
		this.traineeGroup = traineeGroup;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getRoomNumber() {
		return room;
	}

	public void setRoomNumber(String room) {
		this.room = room;
	}
	
	
	
	

}
