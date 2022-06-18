package com.tsemenouski.spring_boot_exchange_rate_comparison;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsemenouski.spring_boot_exchange_rate_comparison.controller.MainPageController;
import com.tsemenouski.spring_boot_exchange_rate_comparison.service.ExchangeRateService;
import com.tsemenouski.spring_boot_exchange_rate_comparison.service.GifService;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;

@RunWith(SpringRunner.class)
@WebMvcTest(MainPageController.class)
public class MainPageControllerTest {

	private final String gifURL = "/comparison/gif/test";
	private final String codesURL = "/comparison/codes";

	@Value("${giphy.rich}")
	private String richTag;
	@Value("${giphy.broke}")
	private String brokeTag;
	@Value("${giphy.error}")
	private String errorTag;
	@Value("${giphy.equal}")
	private String equalTag;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ExchangeRateService exchangeRateService;

	@MockBean
	private GifService gifService;

	@Test
	public void whenReturnListOfCharCodes() throws Exception {
		List<String> responseList = new ArrayList<>();
		responseList.add("TEST");
		Mockito.when(exchangeRateService.getCodes())
				.thenReturn(responseList);
		mockMvc.perform(get(codesURL)
						.content(mapper.writeValueAsString(responseList))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$[0]").value("TEST"));
	}

	@Test
	public void whenListIsNull() throws Exception {
		Mockito.when(exchangeRateService.getCodes())
				.thenReturn(null);
		mockMvc.perform(get(codesURL)
						.content(mapper.writeValueAsString(null))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$[0]").doesNotExist());
	}

	@Test
	public void whenReturnRichGif() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("compareResult", richTag);
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(exchangeRateService.getCompareRates(anyString()))
				.thenReturn(1);
		Mockito.when(gifService.getGif(richTag))
				.thenReturn(responseEntity);
		mockMvc.perform(get(gifURL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.compareResult").value(richTag));
	}

	@Test
	public void whenReturnBrokeGif() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("compareResult", this.brokeTag);
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(exchangeRateService.getCompareRates(anyString()))
				.thenReturn(-1);
		Mockito.when(gifService.getGif(this.brokeTag))
				.thenReturn(responseEntity);
		mockMvc.perform(get(gifURL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.compareResult").value(this.brokeTag));
	}

	@Test
	public void whenReturnEqualGif() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("compareResult", equalTag);
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(exchangeRateService.getCompareRates(anyString()))
				.thenReturn(0);
		Mockito.when(gifService.getGif(equalTag))
				.thenReturn(responseEntity);
		mockMvc.perform(get(gifURL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.compareResult").value(equalTag));
	}

	@Test
	public void whenReturnErrorGif() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("compareResult", errorTag);
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(exchangeRateService.getCompareRates(anyString()))
				.thenReturn(2);
		Mockito.when(gifService.getGif(errorTag))
				.thenReturn(responseEntity);
		mockMvc.perform(get(gifURL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.compareResult").value(errorTag));
	}

	@Test
	public void whenReturnErrorGifAnyOtherKey() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("compareResult", errorTag);
		ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(exchangeRateService.getCompareRates(anyString()))
				.thenReturn(3);
		Mockito.when(gifService.getGif(errorTag))
				.thenReturn(responseEntity);
		mockMvc.perform(get(gifURL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.compareResult").value(errorTag));
	}
}