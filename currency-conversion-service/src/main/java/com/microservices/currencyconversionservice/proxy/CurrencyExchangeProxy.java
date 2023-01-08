package com.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.currencyconversionservice.bean.CurrencyConversion;

// Connect to currency-exchange service
//@FeignClient(name = "currency-exchange", url = "localhost:8000")
@FeignClient(name = "currency-exchange-service") // connect to Eureka ot get the url
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	// Automatically map values from CurrencyExchange to CurrencyConversion
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
