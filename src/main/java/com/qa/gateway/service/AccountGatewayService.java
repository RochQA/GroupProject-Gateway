package com.qa.gateway.service;

import java.util.List;

import com.qa.gateway.entities.Account;
import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.entities.Trainer;
import com.qa.gateway.entities.UpdateAccount;

public interface AccountGatewayService {
	
	public Account createAccount(CreateAccount account);
	
	public Trainer createTrainer(CreateAccount account);
	
	public Account getAccount(Long id);
	
	public List<Account> getAllAccounts();
	
	public Account updateAccount(UpdateAccount upAccount, Account account);
	
	public String deleteAccount(Long id);
	
}
