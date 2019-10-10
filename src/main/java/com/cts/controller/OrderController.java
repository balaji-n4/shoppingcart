/**
 * This class is used to  order information.
 * 
 * @author 764432
 *
 */
package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.model.Order;
import com.cts.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * This constructor is used for initializing OrderService variable.
	 * 
	 * @param orderService
	 */

	public OrderController(final OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	/**
	 * This method is checking the order placed successfully or not.
	 * 
	 * @param order
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping
	public ResponseEntity<Object> placeOrder(@RequestBody final Order order) {
		Order savedOrder = orderService.placeOrder(order);
		if (savedOrder == null) {
			System.out.println("Placing order");
			return new ResponseEntity("Unable to place order", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity("Order placed successfully. Order id is:" + order.getOrderId(), HttpStatus.OK);
		}
	}

	/**
	 * This method is used for canceling the order if it the order is present in
	 * the excel.
	 * 
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/cancel/{orderId}")
	public ResponseEntity<Object> cancelOrder(@PathVariable("orderId") final String orderId) {
		String cancelledOrderId = orderService.cancelOrder(orderId);
		if (cancelledOrderId == null) {
			return new ResponseEntity("Order not found in the system", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity("Order with orderId : " + orderId + " cancelled successfully", HttpStatus.OK);
		}
	}

	/**
	 * This method is used for getting the list of orders.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity(orders, HttpStatus.OK);
	}

	/**
	 * This method is used for getting the order by Id.
	 * 
	 * @param orderId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable("orderId")final String orderId) {
		Order order = orderService.getOrderById(orderId);
		if (order == null) {
			return new ResponseEntity(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity(order, HttpStatus.OK);
		}
	}

}
