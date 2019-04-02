package com.qa.gateway.service;

import org.springframework.stereotype.Service;

import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.entities.Trainer;

@Service
public class AccountGatewayServiceImpl implements AccountGatewayService{	

	@Override
	public Trainer createTrainer(CreateAccount account) {
		Trainer trainer = new Trainer();
		trainer.setEmail(account.getEmail());
		trainer.setFirstName(account.getTrainerFirstName());
		trainer.setLastName(account.getTrainerLastName());
		return trainer;
	}
}
