package com.tsemenouski.spring_boot_exchange_rate_comparison.client;

import com.tsemenouski.spring_boot_exchange_rate_comparison.data.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GifBankClient", url = "${openexchangerates.url.general}")
public interface ExchangeRateClient {

    @GetMapping("/latest.json")
    ExchangeRate getLatestRates(
            @RequestParam("app_id") String appId
    );

    @GetMapping("/historical/{date}.json")
    ExchangeRate getHistoricalRates(
            @PathVariable String date,
            @RequestParam("app_id") String appId
    );
}
