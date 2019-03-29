package com.qa.gateway.service;

import java.util.List;

import com.qa.gateway.entities.Account;
import com.qa.gateway.entities.CreateAccount;

public interface AccountGatewayService {
	
	public Account createAccount(CreateAccount account);
	
	public Account getAccount(Long id);
	
	public List<Account> getAllAccounts();
	
	public Account updateAccount(Account upAccount, Account account);
	
	public String deleteAccount(Long id);
	
}
