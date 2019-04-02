package com.qa.gateway.controller;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.qa.gateway.entities.Constants;
import com.qa.gateway.entities.Trainer;

@RestController
public class TrainerGatewayController {
	
	private RestTemplateBuilder rest;
	private EurekaClient client;

	public TrainerGatewayController(RestTemplateBuilder rest, EurekaClient client) {		
		this.rest = rest;
		this.client = client;
	}
	
	@GetMapping(Constants.GET_TRAINER)
	public Trainer getTrainer(@PathVariable Long trainerId) {
		HttpEntity<Long> entity = new HttpEntity<>(trainerId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_TRAINER_PATH, 
				HttpMethod.GET, entity, Trainer.class).getBody();
	}
	
	@GetMapping(Constants.GET_ALL_TRAINERS)
	public List<Trainer> getAllTrainers(){
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_ALL_TRAINERS_PATH, 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Trainer>>(){}).getBody();	
	}
	
	@DeleteMapping(Constants.DELETE_TRAINER)
	public String deleteTrainer(@PathVariable Long trainerId) {
		HttpEntity<Long> entity = new HttpEntity<>(trainerId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.DELETE_TRAINER_PATH, 
				HttpMethod.DELETE, entity, String.class).getBody();
	}
}
