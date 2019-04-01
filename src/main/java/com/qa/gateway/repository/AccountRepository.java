package com.qa.gateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.gateway.entities.Plan;

public interface AccountRepository extends JpaRepository<Plan, Long> {

}
