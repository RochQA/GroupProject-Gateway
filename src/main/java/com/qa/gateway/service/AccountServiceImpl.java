package com.qa.gateway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qa.gateway.entities.Account;
import com.qa.gateway.entities.Constants;
import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	AccountRepository repo;

	public AccountServiceImpl(AccountRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public String createAccount(CreateAccount account) {
		Account newAcc = new Account();
		newAcc.setEmail(account.getEmail());
		newAcc.setPassword(account.getPassword());
		repo.save(newAcc);
		return Constants.CREATED_ACCOUNT_MESSAGE;
	}

	@Override
	public Account getAccount(Long id) {
		List<Account> allAccounts = getAllAccounts();
		Account gotAccount = new Account();
		gotAccount = allAccounts.stream().filter(account -> id.equals(account.getId())).findAny().orElse(null);
		return gotAccount;
	}

	@Override
	public List<Account> getAllAccounts() {
		return repo.findAll();
	}

	@Override
	public Account updateAccount(Account account) {
		Long id = account.getId();
		Account upAccount = getAccount(id);
		if(upAccount != null) {
			upAccount.setEmail(account.getEmail());
			upAccount.setPassword(account.getPassword());
			repo.save(upAccount); 
		}
		return upAccount;
	}

	@Override
	public String deleteAccount(Long id) {
		repo.deleteById(id);
		return null;
	}

}
