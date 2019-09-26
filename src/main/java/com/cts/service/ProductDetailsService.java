/**
 * This class is used to give the product details.
 * 
 * @author 764432
 *
 */
package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Product;
import com.cts.repository.ProductDetailssRepo;

@Service("productDetailsService")
public class ProductDetailsService {

	@Autowired
	ProductDetailssRepo repo;

	/**
	 * It is used to add a product item into excel.
	 * 
	 * @param pro
	 * @return
	 */
	public String addItem(Product pro) {
		return repo.addItem(pro);
	}

	/**
	 * It is used to remove a product item from excel.
	 * 
	 * @param id
	 * @return
	 */
	public String removeItem(String id) {
		return repo.removeItem(id);
	}

	/**
	 * It is used to give all product details.
	 * 
	 * @return
	 */
	public List<Product> getAllProducts() {
		return repo.getAllProducts();
	}

	/**
	 * It is used to get the product details by Id.
	 * 
	 * @param productId
	 * @return
	 */
	public Product getProductById(String productId) {
		return repo.getProductById(productId);
	}
	
	/**
	 * It is used to update a product item into excel.
	 * 
	 * @param pro
	 * @return
	 */
	public String updateItem(Product pro) {
		return repo.updateItem(pro);
	}
}
