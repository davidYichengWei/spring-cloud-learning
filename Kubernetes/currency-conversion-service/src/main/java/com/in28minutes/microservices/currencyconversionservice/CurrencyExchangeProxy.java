package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//@FeignClient(name="currency-exchange", url="localhost:8000")
//@FeignClient(name="currency-exchange")

//CHANGE-KUBERNETES
// Use CURRENCY_EXCHANGE_SERVICE_HOST if exists, otherwise, use localhost
// CURRENCY_EXCHANGE_SERVICE_HOST will be created by Kubernetes
// @FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
// CURRENCY_EXCHANGE_URI is a custom environment variable
@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_URI:http://localhost}:8000")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(
			@PathVariable String from,
			@PathVariable String to);

}
