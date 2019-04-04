package com.qa.gateway.controller;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.qa.gateway.entities.Constants;

import com.qa.gateway.entities.Plan;
@CrossOrigin(origins="http://35.210.85.248:3000")
@RestController
public class PlanGatewayController {
	

	private RestTemplateBuilder rest;
	private EurekaClient client;

	public PlanGatewayController(RestTemplateBuilder rest, EurekaClient client) {
		this.rest = rest;
		this.client = client;
	}

	@PostMapping(Constants.CREATE_PLAN)
	public String createPlan(@RequestBody Plan plan) {
		String checkRes = (checkPlan(plan));
		if (checkRes.equals(Constants.VALID_MESSAGE)) {
			return savePlan(plan);
		}else return checkRes;
	}
	
	@GetMapping(Constants.GET_PLAN)
	public Plan getPlan(@PathVariable Long planId) {
		HttpEntity<Long> entity = new HttpEntity<>(planId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_PLAN_PATH, 
				HttpMethod.GET, entity, Plan.class).getBody();
	}
	
	@GetMapping(Constants.GET_ALL_PLANS)
	public List<Plan> getAllPlans(){
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_ALL_PLAN_PATH, 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Plan>>(){}).getBody();	
	}
	
	@PutMapping(Constants.UPDATE_PLAN)
	public String updatePlan(@RequestBody Plan plan) {
		String checkRes = (checkUpdatePlan(plan));
		if (checkRes.equals(Constants.VALID_MESSAGE)) {
			return savePlan(plan);
		}else return checkRes;	
	}
	
	@DeleteMapping(Constants.DELETE_PLAN)
	public String deletePlan(@PathVariable Long planId) {
		HttpEntity<Long> entity = new HttpEntity<>(planId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.DELETE_PLAN_PATH, 
				HttpMethod.DELETE, entity, String.class).getBody();
	}
	private String checkUpdatePlan(Plan plan) {
		HttpEntity<Plan> entity = new HttpEntity<>(plan);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.PLAN, false).getHomePageUrl()+Constants.CHECK_UPDATE_PLAN_PATH, 
				HttpMethod.POST, entity, String.class).getBody();
	}
	private String checkPlan(Plan plan) {
		HttpEntity<Plan> entity = new HttpEntity<>(plan);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.PLAN, false).getHomePageUrl()+Constants.CHECK_PLAN_PATH, 
				HttpMethod.POST, entity, String.class).getBody();
	}	
	private String savePlan(Plan plan) {
		HttpEntity<Plan> entity = new HttpEntity<>(plan);
		this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.CREATE_PLAN_PATH, 
				HttpMethod.POST, entity, String.class).getBody();
		return Constants.VALID_MESSAGE;
	}	

}
