package com.tsemenouski.spring_boot_exchange_rate_comparison;

import com.tsemenouski.spring_boot_exchange_rate_comparison.client.GifClient;
import com.tsemenouski.spring_boot_exchange_rate_comparison.service.GifService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.tsemenouski.spring_boot_exchange_rate_comparison")
public class GifServiceTest {

    @Autowired
    private GifService gifService;
    @MockBean
    private GifClient gifClient;

    @Test
    public void whenPositiveChanges() {
        ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(gifClient.getRandomGif(anyString(), anyString()))
                .thenReturn(testEntity);
        ResponseEntity<Map> result = gifService.getGif("gif_test");
        assertEquals("gif_test", result.getBody().get("compareResult"));
    }
}