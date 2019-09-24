/**
 * This class is used to place the order.
 * 
 * @author 806642
 *
 */
package com.cts.controller;

import java.lang.reflect.Array;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.model.Order;
import com.cts.model.Product;
import com.cts.model.User;

@RestController
public class BuyProductController {
	
/**
 * The RestTemplate offers templates for common scenarios by HTTP method, in
 * addition to the generalized {@code exchange} and {@code execute} methods that
 * support of less frequent cases.
 * 
 */
	private RestTemplate template;

	/**
	 * This method is used for placing the order.
	 * 
	 * @param user
	 * @return String
	 */
	@RequestMapping(value = "/placeorder", method = RequestMethod.POST)
	public String placeOrder(@RequestBody final User user) {

		String userId = "";
		String productId = "";
		template = new RestTemplate();

		String response = template.postForObject("http://localhost:9090/auth/login", user, String.class);

		if (response.contains("invalid")) {
			return response;
		}
		String[] arr = response.split(":");
		int one1 = 1;
		if (arr.length >= one1) {
			userId = (String) Array.get(arr, 1);
		}
		Product product = new Product();
		product.setProdId("102");
		product.setProdName("nokia");
		product.setPrice("999");
		String productResponse = template.postForObject("http://localhost:9090/products", product, String.class);

		String[] productarr = productResponse.split(":");
		int oNe1 = 1;
		if (productarr.length >= oNe1) {
			productId = (String) Array.get(productarr, 1);
		}

		Product getProduct = template.getForObject("http://localhost:9090/products/" + productId, Product.class);

		Order order = new Order();
		order.setUserID(userId);
		order.setOrderDate("2019-Sep-13");
		order.setOrderId("od-856429");
		order.setProdId(getProduct.getProdId());

		/*
		 * final String orderResponse =
		 * template.postForObject("http://localhost:9090/products/orders",
		 * order, String.class);
		 * 
		 * return orderResponse;
		 */
		return template.postForObject("http://localhost:9090/products/orders", order, String.class);
	}
}
