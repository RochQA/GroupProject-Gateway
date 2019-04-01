package com.qa.gateway.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;
import com.qa.gateway.entities.Account;
import com.qa.gateway.entities.Constants;
import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.entities.Plan;
import com.qa.gateway.repository.AccountRepository;
import com.qa.gateway.service.PlanGatewayServiceImpl;
@RestController
public class PlanGatewayController {
	
	private PlanGatewayServiceImpl srvc;
	private RestTemplateBuilder rest;
	private EurekaClient client;
	@Autowired
	AccountRepository repo;

	public PlanGatewayController(PlanGatewayServiceImpl srvc, RestTemplateBuilder rest, EurekaClient client) {
		this.srvc = srvc;
		this.rest = rest;
		this.client = client;
	}
	@PostMapping("/createAPlan")
	public String createAPlan(@RequestBody Plan plan) {
		repo.save(plan);
		return "Saved";
	}
	@PostMapping("/createPlan")
	public String createPlan(@RequestBody Plan plan) {
		String checkRes = (checkPlan(plan));
		if (checkRes.equals("Valid")) {
//			return savePlan(plan);
			repo.save(plan);
			return "created";
		}else return checkRes;
	}
	
	@GetMapping("/getPlan/{planId}")
	public Optional<Plan> getPlan(@PathVariable Long planId) {
		HttpEntity<Long> entity = new HttpEntity<>(planId);
//		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_PLAN_PATH, 
//				HttpMethod.GET, entity, Plan.class).getBody();
		return repo.findById(planId);
	}
	
	@GetMapping("/getAllPlans")
	public List<Plan> getAllPlans(){
//		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_ALL_PLAN_PATH, 
//				HttpMethod.GET, null, new ParameterizedTypeReference<List<Plan>>(){}).getBody();
		return repo.findAll();
	
	}
	
	@PutMapping("/updatePlan")
	public String updatePlan(@RequestBody Plan plan) {
		
		return null;	
	}
	
	@DeleteMapping("/deletePlan/{planId}")
	public String deletePlan(@PathVariable Long planId) {
//		HttpEntity<Long> entity = new HttpEntity<>(planId);
//		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.DELETE_PLAN_PATH, 
//				HttpMethod.DELETE, entity, String.class).getBody();
		repo.deleteById(planId);
		return "deleted";
	}
	private String checkPlan(Plan plan) {
		HttpEntity<Plan> entity = new HttpEntity<>(plan);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.PLAN, false).getHomePageUrl()+Constants.CHECK_PLAN_PATH, 
				HttpMethod.POST, entity, String.class).getBody();
	}	
	private String savePlan(Plan plan) {
//		HttpEntity<Plan> entity = new HttpEntity<>(plan);
//		this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.CREATE_PLAN_PATH, 
//				HttpMethod.POST, entity, String.class).getBody();
		return Constants.VALID_MESSAGE;
	}	

}
