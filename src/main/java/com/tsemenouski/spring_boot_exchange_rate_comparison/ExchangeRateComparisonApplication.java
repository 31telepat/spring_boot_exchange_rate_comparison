package com.tsemenouski.spring_boot_exchange_rate_comparison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeRateComparisonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateComparisonApplication.class, args);
	}

}
