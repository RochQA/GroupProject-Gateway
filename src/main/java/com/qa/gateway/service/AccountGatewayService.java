package com.qa.gateway.service;


import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.entities.Trainer;

public interface AccountGatewayService {
	
	public Trainer createTrainer(CreateAccount account);
	
	
}
