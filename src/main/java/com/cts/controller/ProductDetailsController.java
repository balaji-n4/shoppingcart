/**
 * This class is used to get the product information.
 * @author
 */
package com.cts.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.Order;
import com.cts.model.Product;
import com.cts.service.OrderService;
import com.cts.service.ProductDetailsService;

@RestController
@RequestMapping("/products")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProductDetailsController {

	@Autowired
	private ProductDetailsService productDetailsService;

	@Autowired
	private OrderService orderService;

	/**
	 * This constructor is used to initialize ProductDetailsService.
	 * 
	 * @param productDetailsService
	 * @param orderService
	 */
	public ProductDetailsController(ProductDetailsService productDetailsService, OrderService orderService) {
		super();
		this.productDetailsService = productDetailsService;
		this.orderService = orderService;
	}

	/**
	 * This method is used for adding the item into excel.
	 * 
	 * @param product
	 * @return
	 */

	@PostMapping
	public ResponseEntity<String> addItem(@RequestBody Product product) {
		String addItemResponse = productDetailsService.addItem(product);
		if(addItemResponse != null) {
			return new ResponseEntity(addItemResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity(addItemResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method is used for removing an item from excel.
	 * 
	 * @param prodId
	 * @return
	 */
	@DeleteMapping(path = "/{prodId}")
	public ResponseEntity<String> removeItem(@PathVariable("prodId") final String prodId) {
		String proId = productDetailsService.removeItem(prodId);
		if (proId == null) {
			return new ResponseEntity("Product not found in the system", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity("Product with prodId : " + prodId + " removed successfully", HttpStatus.OK);
		}
	}

	/**
	 * This method is used for get all the product.
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productDetailsService.getAllProducts();
		return new ResponseEntity(products, HttpStatus.OK);
	}

	/**
	 * This method is used for get the product by product Id.
	 * 
	 * @param prodId
	 * @return
	 */
	@GetMapping("/{prodId}")
	public ResponseEntity<Product> getProductById(@PathVariable("prodId") final String prodId) {
		Product product = productDetailsService.getProductById(prodId);
		if (product == null) {
			return new ResponseEntity("Product not found", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity(product, HttpStatus.OK);
		}
	}

	/**
	 * This method is used for save the order.
	 * 
	 * 
	 * @param order
	 * @return
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public ResponseEntity<String> save(@Valid @RequestBody final Order order) {
		String response = orderService.save(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	/**
	 * This method is used for update product details.
	 * 
	 * 
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "/{prodId}", method = RequestMethod.PUT)
	public ResponseEntity updateProduct(@PathVariable("prodId") final String prodId, @Valid @RequestBody final Product product) {
		Product existingObj = productDetailsService.getProductById(prodId);
		if (existingObj != null) {
			productDetailsService.updateItem(product);
			return new ResponseEntity(product, HttpStatus.OK);
		} else {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
			
		}
	}
}
