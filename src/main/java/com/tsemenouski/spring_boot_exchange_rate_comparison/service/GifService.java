package com.tsemenouski.spring_boot_exchange_rate_comparison.service;

import com.tsemenouski.spring_boot_exchange_rate_comparison.client.GifClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GifService {

    private GifClient gifClient;
    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GifService(GifClient gifClient) {
        this.gifClient = gifClient;
    }

    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> result = gifClient.getRandomGif(apiKey, tag);
        result.getBody().put("compareResult", tag);

        return result;
    }
}
