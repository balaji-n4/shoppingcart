package com.cts.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Order;
import com.cts.model.Product;
import com.cts.repository.ProductDetailssRepo;
import com.cts.service.OrderService;
import com.cts.service.ProductDetailsService;
import com.cts.util.ProductExcelFile;

public class ProductDetailsControllerTest extends AbstractTest {

	@InjectMocks
	ProductDetailsController productDetailsController;
	
	@InjectMocks
	OrderController orderController;

	@Mock
	private ProductDetailsService productDetailsService;

	@Mock
	private ProductExcelFile productExcelFile;

	@Mock
	private ProductDetailssRepo productDetailssRepo;
	
	@Mock
	private OrderService orderService;
	
	@Mock
	private RestTemplate restTemplate;

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void testAddItem() {
		File file;
		file = new File("./src/main/resources/excel/product.xlsx");
		Product product = new Product();
		product.setProdId("112");
		product.setProdName("TV");
		product.setPrice("1000");
		String Response = "Product Added Successfully";
		when(productDetailsService.addItem(product)).thenReturn(Response);
		ResponseEntity<String> response = productDetailsController.addItem(product);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void testRemoveItemSuccess() throws Exception {
		when(productDetailsService.removeItem("PROD-12")).thenReturn("abcd");
		ResponseEntity<String> response = productDetailsController.removeItem("PROD-12");
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testRemoveItemFailure() throws Exception {
		when(productDetailsService.removeItem("12")).thenReturn(null);
		ResponseEntity<String> response = productDetailsController.removeItem(null);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}

	@Test
	public void testRemoveItem() {
		Product product = new Product();
		product.setProdId("1");
		product.setProdName("A");
		product.setPrice("10000");
		when(productDetailsService.removeItem(product.getProdId())).thenReturn("Removed");
		productDetailsController.removeItem(product.getProdId());
	}
	@Test
	public void testAddItemFailure() {
		Product product = new Product();
		product.setProdId("PROD-321");
		product.setProdName("TV");
		product.setPrice("1000.0");
		when(productDetailsService.addItem(product)).thenReturn("Product not added");
		ResponseEntity<String> response = productDetailsController.addItem(null);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
	}
	@Test
	public void testGetAllProductsSuccess() throws Exception {
		String uri = "/products";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Order[] orderlist = super.mapFromJson(content, Order[].class);
		assertNotNull(mvcResult.getResponse());
		assertTrue(orderlist.length > 0);
	}
	
	@Test
	public void testGetProductBuIdSuccess() throws Exception {
		when(productDetailsService.getProductById("PROD-321")).thenReturn(new Product());
		ResponseEntity<Product> response = productDetailsController.getProductById("PROD-321");
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void testGetProductBuIdFailure() throws Exception {
		when(productDetailsService.getProductById("PROD-321")).thenReturn(null);
		productDetailsController.getProductById(null);
	}
	
	@Test
	public void testSave() {
		Order order = new Order();
		order.setOrderId("OR-123");
		order.setOrderDate("2019-07-11");
		order.setProdId("PROD-123");
		order.setUserID("USR-123");
		when(orderService.save(order)).thenReturn("User Order Placed Successfully");
		ResponseEntity<String> response = productDetailsController.save(order);
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testUpdateProduct() {
		Product product = new Product();
		product.setProdId("PROD-321");
		product.setProdName("HD-TV");
		product.setPrice("1000.0");
		when(productDetailsService.getProductById(Mockito.anyString())).thenReturn(product);
		when(productDetailsService.addItem(Mockito.any(Product.class))).thenReturn("Product updated Successfully");
		ResponseEntity<String> response = productDetailsController.updateProduct(product.getProdId(), product);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	@Test
	public void testUpdateProductFail() {
		Product product = new Product();
		product.setProdId("PROD-321");
		product.setProdName("HD-TV");
		product.setPrice("1000.0");
		when(productDetailsService.getProductById(Mockito.anyString())).thenReturn(null);
		ResponseEntity<String> response = productDetailsController.updateProduct(product.getProdId(), product);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
	}
	
}
