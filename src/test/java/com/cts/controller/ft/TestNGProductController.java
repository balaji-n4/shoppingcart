package com.cts.controller.ft;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Product;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestNGProductController {

	@Test
	public void testAddItem() throws Exception {

		String url = "http://localhost:9090/products";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Product product = new Product();
		product.setProdId("PROD-777");
		product.setProdName("Laptop");
		product.setPrice("100000");

		String inputJson = mapToJson(product);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		/*
		 * String response = restTemplate.postForObject(url, request,
		 * String.class); assertEquals(true, response.contains(""));
		 */
	}

	@Test
	public void testCancelOrderSuccess() throws Exception {

		String postUrl = "http://localhost:9090/products";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Product product = new Product();
		product.setProdId("PROD-222");
		product.setProdName("Keyboard");
		product.setPrice("20000");
		String inputJson = mapToJson(product);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(postUrl, request, String.class);
		assertEquals(true, response.contains("Product Added Successfully"));

		String cancelUrl = "http://localhost:9090/products/PROD-222";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String response1 = restTemplate.getForObject(cancelUrl, String.class);
		assertEquals(true, response.contains("Added Successfully"));

	}

	@Test
	public void testGetProductByIdSuccess() throws Exception {
		String url = "http://localhost:9090/products";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Product product = new Product();
		product.setProdId("PROD-2212");
		product.setProdName("Keyboard");
		product.setPrice("20000");

		String inputJson = mapToJson(product);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		String response = restTemplate.postForObject(url, request, String.class);
		assertEquals(true, response.contains("Product Added"));

		String url1 = "http://localhost:9090/products/PROD-2212";
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String response1 = restTemplate.getForObject(url1, String.class);
		Product p = mapFromJson(response1, Product.class);
		assertEquals(product.getProdId(), p.getProdId());

	}

	@Test
	public void testGetAllProductsSuccess() throws Exception {
		String url = "http://localhost:9090/products";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Product[] prodList = restTemplate.getForObject(url, Product[].class);
		List<Product> pList = Arrays.asList(prodList);

		assertEquals(true, pList.size() > 0);

	}

	@Test
	public void testAddItemAndDeleteItem() throws Exception {

		String url = "http://localhost:9090/products";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Product product = new Product();
		product.setProdId("PROD-2019");
		product.setProdName("Laptop");
		product.setPrice("200000");

		String inputJson = mapToJson(product);
		HttpEntity<String> request = new HttpEntity<String>(inputJson, headers);
		// Create a product
		String response = restTemplate.postForObject(url, request, String.class);
		assertEquals(response, "Product Added Successfully,Product Id:  PROD-2019");

		Map<String, String> params = new HashMap<String, String>();
		params.put("prodId", product.getProdId());

		url = "http://localhost:9090/products/{prodId}";
		// Update product
		product.setProdName("NewLaptop");
		restTemplate.put(url, product, params);

		Product returnedProduct = restTemplate.getForObject(url, Product.class, params);
		assertEquals(product.getProdId(), returnedProduct.getProdId());
		assertEquals(product.getPrice(), returnedProduct.getPrice());
		assertEquals(product.getProdName(), returnedProduct.getProdName());

		// Delete product
		restTemplate.delete(url, params);

	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
