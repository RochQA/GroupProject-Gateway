package com.qa.gateway.service;

import java.util.List;

import com.qa.gateway.entities.Plan;

public interface PlanGatewayService {
	
	public Plan createPlan(Plan plan);
	
	public Plan getPlan(Long id);
	
	public List<Plan> getAllPlans();
	
	public Plan updatePlan(Plan updPlan, Plan plan);
	
	public String deletePlan(Long id);
	
}
