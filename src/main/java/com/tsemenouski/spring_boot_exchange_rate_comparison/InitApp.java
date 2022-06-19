package com.tsemenouski.spring_boot_exchange_rate_comparison;

import com.tsemenouski.spring_boot_exchange_rate_comparison.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitApp {
    private ExchangeRateService exchangeRateService;

    @Autowired
    public InitApp(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @PostConstruct
    public void firstDataInit() {
        exchangeRateService.getCurrentRates();
        exchangeRateService.getLastRates();
    }
}
