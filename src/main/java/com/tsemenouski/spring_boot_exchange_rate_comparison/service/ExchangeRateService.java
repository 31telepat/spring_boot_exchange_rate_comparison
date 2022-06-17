package com.tsemenouski.spring_boot_exchange_rate_comparison.service;

import com.tsemenouski.spring_boot_exchange_rate_comparison.client.ExchangeRateClient;
import com.tsemenouski.spring_boot_exchange_rate_comparison.data.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    private ExchangeRate lastRate;
    private ExchangeRate currentRate;
    private ExchangeRateClient exchangeRatesClient;
    @Value("${openexchangerates.app.id}")
    private String appId;
    @Value("${openexchangerates.base}")
    private String base;


    @Autowired
    public ExchangeRateService(ExchangeRateClient exchangeRatesClient) {
        this.exchangeRatesClient = exchangeRatesClient;
    }

    public List<String> getCodes() {
        List<String> result = null;
        try {
            result = new ArrayList<>(currentRate.getRates().keySet());
        } catch (NullPointerException e){
            e.getMessage();
        }

        return result;
    }

    public int getCompareRates(String charCode) {
        Double lastCurrency = getCurrency(lastRate, charCode);
        Double currentCurrency = getCurrency(currentRate, charCode);

        return Double.compare(currentCurrency, lastCurrency);
    }

    public void getCurrentRates() {
        try {
            currentRate = exchangeRatesClient.getLatestRates(appId);
        }catch (Exception exception){
            exception.getMessage();
        }
    }

    public void getLastRates() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String lastDate = dateFormat.format(calendar.getTime());
        try {
            lastRate = exchangeRatesClient.getHistoricalRates(lastDate, appId);
        } catch(Exception exception){
            exception.getMessage();
        }
    }

    private Double getCurrency(ExchangeRate rate, String charCode) {
        Double result = null;
        Double selectRate = null;
        Double appRate = null;
        Map<String, Double> map;

        try {
            map = rate.getRates();
            selectRate = map.get(charCode);
            appRate = map.get(base);
        }catch (NullPointerException exception){
            exception.getMessage();
        }

        try {
            result = new BigDecimal(selectRate / appRate)
                    .setScale(6, RoundingMode.HALF_UP)
                    .doubleValue();
        }catch (NullPointerException exception){
            exception.getMessage();
        }

        return result;
    }
}
