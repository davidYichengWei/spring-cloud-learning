package com.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		
		// Build custom routes
		return builder.routes()
				.route(p -> p.path("/get")
						.filters(f -> f.addRequestHeader("MyHeader", "MyURI") // Can append a custom header to request
									   .addRequestParameter("Param", "MyValue")) // Add parameter to the request
						.uri("http://httpbin.org:80"))
				// For all requests to /currency-exchange, route it to Eureka and load balance
				.route(p -> p.path("/currency-exchange/**")
						.uri("lb://currency-exchange")) 
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				// currency-conversion-feign should also be redirected to currency-conversion service!
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				// redirect /currency-conversion-new to /currency-conversion-feign
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}
