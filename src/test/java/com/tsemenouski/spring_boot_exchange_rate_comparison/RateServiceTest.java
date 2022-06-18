package com.tsemenouski.spring_boot_exchange_rate_comparison;

import com.tsemenouski.spring_boot_exchange_rate_comparison.client.ExchangeRateClient;
import com.tsemenouski.spring_boot_exchange_rate_comparison.data.ExchangeRate;
import com.tsemenouski.spring_boot_exchange_rate_comparison.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.tsemenouski.spring_boot_exchange_rate_comparison")
public class RateServiceTest {

    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @MockBean
    private ExchangeRateClient exchangeRateClient;

    private ExchangeRate currentRates;
    private ExchangeRate lastRates;

    @BeforeEach
    public void init() {
        int time = 1341936000;
        this.currentRates = new ExchangeRate();
        this.currentRates.setTimestamp(time);
        this.currentRates.setBase("TEST_BASE");
        Map<String, Double> currentRatesMap = new HashMap<>();
        currentRatesMap.put("EQUAL", 1.0);
        currentRatesMap.put("DOWN", 1.0);
        currentRatesMap.put("UP", 2.0);
        currentRatesMap.put(base, 99.999999);
        currentRatesMap.put("TEST_BASE", 1.0);
        currentRates.setRates(currentRatesMap);

        time = 1741936000;
        lastRates = new ExchangeRate();
        lastRates.setTimestamp(time);
        lastRates.setBase("TEST_BASE");
        Map<String, Double> lastRatesMap = new HashMap<>();
        lastRatesMap.put("EQUAL", 1.0);
        lastRatesMap.put("DOWN", 2.0);
        lastRatesMap.put("UP", 1.0);
        lastRatesMap.put(base, 99.999999);
        lastRatesMap.put("TEST_BASE", 1.0);
        lastRates.setRates(lastRatesMap);
    }

    @Test
    public void whenUpChanges() {
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("UP");
        assertEquals(1, result);
    }

    @Test
    public void whenDownChanges() {
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("DOWN");
        assertEquals(-1, result);
    }

    @Test
    public void whenNoChanges() {
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("EQUAL");
        assertEquals(0, result);
    }

    @Test
    public void whenGotNull() {
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates(null);
        assertEquals(2, result);
    }

    @Test
    public void whenGotOtherCharCode() {
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("OTHER");
        assertEquals(2, result);
    }

    @Test
    public void whenCurrentIsNull() {
        currentRates = null;
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("EQUAL");
        assertEquals(2, result);
    }

    @Test
    public void whenLastIsNull() {
        lastRates = null;
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("EQUAL");
        assertEquals(2, result);
    }

    @Test
    public void whenCurrentAndLastIsNull() {
        currentRates = null;
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates("EQUAL");
        assertEquals(2, result);
    }

    @Test
    public void whenAppBaseIsChanged() {
        this.currentRates.getRates().put(base, 99.999999);
        Mockito.when(exchangeRateClient.getLatestRates(anyString()))
                .thenReturn(currentRates);
        Mockito.when(exchangeRateClient.getHistoricalRates(anyString(), anyString()))
                .thenReturn(lastRates);
        int result = exchangeRateService.getCompareRates(base);
        assertEquals(0, result);
    }
}