package com.qa.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.gateway.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
