package com.qa.gateway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qa.gateway.entities.Account;
import com.qa.gateway.entities.CreateAccount;
import com.qa.gateway.entities.Trainer;
import com.qa.gateway.entities.UpdateAccount;
import com.qa.gateway.repository.AccountRepository;

@Service
public class AccountGatewayServiceImpl implements AccountGatewayService{
	
	AccountRepository repo;

	public AccountGatewayServiceImpl(AccountRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Account createAccount(CreateAccount account) {
		Account newAcc = new Account();
		newAcc.setEmail(account.getEmail());
		newAcc.setPassword(account.getPassword());
		return newAcc;
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
		return null;
	}
	@Override
	public Account updateAccount(UpdateAccount upAccount, Account account) {
		if(upAccount != null) {
			account.setEmail(upAccount.getEmail());
			account.setPassword(upAccount.getPassword());
		}
		return account;
	}
	@Override
	public String deleteAccount(Long id) {
		repo.deleteById(id);
		return null;
	}

	@Override
	public Trainer createTrainer(CreateAccount account) {
		Trainer trainer = new Trainer();
		trainer.setEmail(account.getEmail());
		trainer.setFirstName(account.getTrainerFirstName());
		trainer.setLastName(account.getTrainerLastName());
		return trainer;
	}
}
