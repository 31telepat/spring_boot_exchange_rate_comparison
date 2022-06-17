package com.tsemenouski.spring_boot_exchange_rate_comparison;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.tsemenouski.spring_boot_exchange_rate_comparison.client.ExchangeRateClient;
import com.tsemenouski.spring_boot_exchange_rate_comparison.client.GifClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableConfigurationProperties
//@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@ContextConfiguration(classes = { WireMockConfig.class })
public class ExchangeRateComparisonApplicationTests {

	@Autowired
	private WireMockServer mockRateService;

	@Autowired
	private WireMockServer mockGifService;

	@Autowired
	private ExchangeRateClient rateClient;

	@Autowired
	private GifClient gifClient;

	@BeforeEach
	void setUp() throws IOException {
		RateMock.setupMockRateResponse(mockRateService);
		GifMock.setupMockGirResponse(mockGifService);
	}

	@Test
	void shouldGetGif(){

	}



//	@Test
//	public void whenPositiveChanges() {
//		ResponseEntity<Map> testEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
//		Mockito.when(gifClient.getRandomGif(anyString(), anyString()))
//				.thenReturn(testEntity);
//		ResponseEntity<Map> result = mockGifService.("gifAppId", "broke");
//		assertEquals("broke", result.getBody().get("compareResult"));
//	}
//
//	@Test
//	void shouldCallRateService() throws Exception{
//		stubFor(get(urlEqualTo("historical/2012-07-10.json?app_id=rateAppId"))
//				.willReturn(aResponse()
//						.withBody(FileLoader.read("classpath:rateApiResponse.json"))
//						.withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//						.withStatus(200)));
//	}
//
//	@Test
//	void shouldCallGifService() {
//
//	}
}
