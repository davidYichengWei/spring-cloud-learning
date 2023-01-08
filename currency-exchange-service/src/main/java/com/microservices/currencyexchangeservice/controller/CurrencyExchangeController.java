package com.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.currencyexchangeservice.bean.CurrencyExchange;
import com.microservices.currencyexchangeservice.jpa.CurrencyExchangeRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	// Interface representing the environment in which the current application is running. 
	// Models two key aspects of the application environment: profiles and properties.
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@CircuitBreaker(name = "default")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		logger.info("retrieveExchangeValue called with {} to {}", from, to);
		
		CurrencyExchange currencyExchange =repository.findByFromAndTo(from, to);
		
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to find data for " + from + " to " + to);
		}
		
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}
}
