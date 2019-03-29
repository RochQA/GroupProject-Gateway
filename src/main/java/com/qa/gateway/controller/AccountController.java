package com.qa.gateway.controller;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
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
import com.qa.gateway.entities.Login;
import com.qa.gateway.service.AccountServiceImpl;
@RestController
public class AccountController {
	
	private AccountServiceImpl srvc;
	private RestTemplateBuilder rest;
	private EurekaClient client;

	public AccountController(AccountServiceImpl srvc, RestTemplateBuilder rest, EurekaClient client) {
		this.srvc = srvc;
		this.rest = rest;
		this.client = client;
	}
	@PostMapping("/createAccount")
	public String createAccount(@RequestBody CreateAccount account) {
		String validResponse = checkValid(account);
		if(validResponse.equals(Constants.VALID_MESSAGE)) {
			String duplicateResponse = checkDuplicates(account);
			if(duplicateResponse.equals(Constants.VALID_MESSAGE)) {
				account.setPassword(encrypt(account.getPassword()));
				return srvc.createAccount(account);
			}else return duplicateResponse;
		}else return validResponse;
	}
	@PutMapping("/login")
	public Account login(@RequestBody Login login) {
		login.setPassword(encrypt(login.getPassword()));
		HttpEntity<Login> entity = new HttpEntity<>(login);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.LOGIN_PATH, 
				HttpMethod.PUT, entity, Account.class).getBody();
	}
	@GetMapping("/getAccount/{accountId}")
	public Account getAccount(@PathVariable Long accountId) {
		return srvc.getAccount(accountId);
	}
	
	@GetMapping("/getAllAccounts")
	public List<Account> getAllAccounts(){
		return srvc.getAllAccounts();
	}
	
	@PutMapping("/updateAccount")
	public Account updateAccount(@RequestBody Account account) {
		return srvc.updateAccount(account);
	}
	
	@DeleteMapping("/deleteAccount/{accountId}")
	public String deleteAccount(@PathVariable Long accountId) {
		return srvc.deleteAccount(accountId);
	}
	
	public String checkValid(CreateAccount account) {
		HttpEntity<CreateAccount> entity = new HttpEntity<>(account);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.CHECK_VALID_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();
	}
	public String checkDuplicates(CreateAccount account) {
		HttpEntity<CreateAccount> entity = new HttpEntity<>(account);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.CHECK_DUPLICATES_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();
	}
	public String encrypt(String password) {
		HttpEntity<String> entity = new HttpEntity<>(password);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.ENCRYPT_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();		
	}

}
