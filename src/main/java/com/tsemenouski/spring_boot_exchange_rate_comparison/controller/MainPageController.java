package com.tsemenouski.spring_boot_exchange_rate_comparison.controller;

import com.tsemenouski.spring_boot_exchange_rate_comparison.service.ExchangeRateService;
import com.tsemenouski.spring_boot_exchange_rate_comparison.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comparison")
public class MainPageController {

    private ExchangeRateService exchangeRateService;
    private GifService gifService;

    @Value("${giphy.rich}")
    private String richTag;
    @Value("${giphy.broke}")
    private String brokeTag;
    @Value("${giphy.error}")
    private String errorTag;
    @Value("${giphy.equal}")
    private String equalTag;

    @Autowired
    public MainPageController(ExchangeRateService exchangeRateService, GifService gifService) {
        this.exchangeRateService = exchangeRateService;
        this.gifService = gifService;
    }

    @GetMapping("/codes")
    public List<String> getCharCodes() {
        return exchangeRateService.getCodes();
    }

    @GetMapping("/gif/{code}")
    public ResponseEntity<Map> getGif(@PathVariable String code) {
        Integer gifKey = null;
        String gifTag = this.errorTag;
        if (code != null) {
            gifKey = exchangeRateService.getCompareRates(code);
            switch (gifKey) {
                case 1 -> gifTag = this.richTag;
                case -1 -> gifTag = this.brokeTag;
                case 0 -> gifTag = this.equalTag;
            }
        }

        return gifService.getGif(gifTag);
    }
}
