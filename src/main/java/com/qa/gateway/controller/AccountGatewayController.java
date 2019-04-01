package com.qa.gateway.controller;

import java.util.List;

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
import com.qa.gateway.entities.Login;
import com.qa.gateway.entities.Trainer;
import com.qa.gateway.entities.UpdateAccount;
import com.qa.gateway.service.AccountGatewayServiceImpl;
@RestController
public class AccountGatewayController {
	
	private AccountGatewayServiceImpl srvc;
	private RestTemplateBuilder rest;
	private EurekaClient client;

	public AccountGatewayController(AccountGatewayServiceImpl srvc, RestTemplateBuilder rest, EurekaClient client) {
		this.srvc = srvc;
		this.rest = rest;
		this.client = client;
	}
	@PostMapping("/createAccount")
	public String createAccount(@RequestBody CreateAccount account) {
		String checkResponse = checkValid(account);
		if(checkResponse.equals(Constants.VALID_MESSAGE)) {
			String trainerResponse = checkTrainer(account);
			if(trainerResponse.equals("Valid")) {
				Long trainerId = saveTrainer(srvc.createTrainer(account));
				account.setPassword(encrypt(account.getPassword()));
				account.setTrainerId(trainerId);
				saveAccount(srvc.createAccount(account));				
				return "New trainer and account created";
			}else return trainerResponse;
		}else return checkResponse;

	}
	@GetMapping("/getAccount/{accountId}")	
	public Account getAccount(@PathVariable Long accountId) {
		HttpEntity<Long> entity = new HttpEntity<>(accountId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_ACCOUNT_PATH, 
				HttpMethod.PUT, entity, Account.class).getBody();
	}
	
	@GetMapping("/getAllAccounts")
	public List<Account> getAllAccounts(){
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.GET_ALL_ACCOUNT_PATH, 
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Account>>(){}).getBody();
	}
	
	@PutMapping("/updateAccount")
	public String updateAccount(@RequestBody UpdateAccount account) {
		Account oldAccount = getAccount(account.getId());
		String checkUpdate = checkUpdateValid(account);
		if(checkUpdate.equals("Valid")) {
			Account updAccount = srvc.updateAccount(account, oldAccount);
//			if(oldAccount!=updAccount) {
			updAccount.setPassword(encrypt(updAccount.getPassword()));
				return saveAccount(updAccount);
//			}else return "nothing changed";
		}else return checkUpdate;		
	}
	
	@DeleteMapping("/deleteAccount/{accountId}")
	public String deleteAccount(@PathVariable Long accountId) {
		HttpEntity<Long> entity = new HttpEntity<>(accountId);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.DELETE_ACCOUNT_PATH, 
				HttpMethod.DELETE, entity, String.class).getBody();
	}
	@PutMapping("/login")
	public Account login(@RequestBody Login login) {
		HttpEntity<Login> entity = new HttpEntity<>(login);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.LOGIN_PATH, 
				HttpMethod.PUT, entity, Account.class).getBody();
	}	
	private String saveAccount(Account account) {
		HttpEntity<Account> entity = new HttpEntity<>(account);
		this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.CREATE_ACCOUNT_PATH, 
				HttpMethod.POST, entity, String.class).getBody();
		return Constants.VALID_MESSAGE;
	}	
	private Long saveTrainer(Trainer trainer) {
		HttpEntity<Trainer> entity = new HttpEntity<>(trainer);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.GETTER, false).getHomePageUrl()+Constants.CREATE_TRAINER_PATH, 
				HttpMethod.POST, entity, Long.class).getBody();
		
	}	
	private String checkValid(CreateAccount account) {
		HttpEntity<CreateAccount> entity = new HttpEntity<>(account);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.CHECK_VALID_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();
	}
	private String checkUpdateValid(UpdateAccount account) {
		HttpEntity<UpdateAccount> entity = new HttpEntity<>(account);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.CHECK_UPDATE_VALID_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();
	}
	private String checkTrainer(CreateAccount account) {
		HttpEntity<CreateAccount> entity = new HttpEntity<>(account);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.TRAINER, false).getHomePageUrl()+Constants.CHECK_VALID_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();
	}

	private String encrypt(String password) {
		HttpEntity<String> entity = new HttpEntity<>(password);
		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.ACCOUNT, false).getHomePageUrl()+Constants.ENCRYPT_PATH, 
				HttpMethod.PUT, entity, String.class).getBody();		
	}
//	private String checkTrainer(String fName, String lName) {
//		HttpEntity<String> entity = new HttpEntity<>(fName+lName);
//		return this.rest.build().exchange(client.getNextServerFromEureka(Constants.TRAINER, false).getHomePageUrl()+Constants.ENCRYPT_PATH, 
//				HttpMethod.PUT, entity, String.class).getBody();
//	}

}
