package com.microservices.currencyexchangeservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.currencyexchangeservice.bean.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{
	
	// Just follow the syntax, no need to implement
	CurrencyExchange findByFromAndTo(String from, String to);
}
