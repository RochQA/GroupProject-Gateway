package com.qa.gateway.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plan {
	
	@Id
	private Long Id;
	
	private Date startDate;
	
	private Date endDate;
		
	private Topic topic;
		
	private TraineeGroup traineeGroup;
	
	private Trainer trainer;
	
	private Room room;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public TraineeGroup getTraineeGroup() {
		return traineeGroup;
	}

	public void setTraineeGroup(TraineeGroup traineeGroup) {
		this.traineeGroup = traineeGroup;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}	

}
