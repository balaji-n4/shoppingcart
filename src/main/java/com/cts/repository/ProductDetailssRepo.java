/**
 * This class is used to give product details
 * 
 * @author 764432
 *
 */
package com.cts.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cts.model.Product;
import com.cts.util.ProductExcelFile;
import com.cts.util.RWExcelProduct;

@Repository("productDetailssRepo")

public class ProductDetailssRepo {

	@Autowired
	private ProductExcelFile productExcelFile;

	@Autowired
	private RWExcelProduct rwExcelProduct;

	/**
	 * Defined constructor
	 * 
	 * @param productExcelFile,rwExcelProduct
	 * 
	 */
	public ProductDetailssRepo(ProductExcelFile productExcelFile, RWExcelProduct rwExcelProduct) {
		super();
		this.productExcelFile = productExcelFile;
		this.rwExcelProduct = rwExcelProduct;
	}

	/**
	 * It will add a product details
	 * 
	 * @param pro
	 * @return
	 */
	public String addItem(final Product pro) {
		return productExcelFile.addItemInExcel("./src/main/resources/excel/product.xlsx", pro);
	}

	/**
	 * Removed the product by using the product Id
	 * 
	 * @param id
	 * @return
	 */
	public String removeItem(final String prodId) {
		return productExcelFile.removeItemFromExcel("./src/main/resources/excel/product.xlsx", prodId);
	}

	/**
	 * retrieve product list.
	 * 
	 * @param
	 * @return list of product
	 */
	public List<Product> getAllProducts() {
		return rwExcelProduct.getAllProducts();
	}

	/**
	 * Retrieving product by using id
	 * 
	 * @param prodId
	 * @return Product
	 */
	public Product getProductById(final String prodId) {
		return rwExcelProduct.getProductById(prodId);
	}
	
	/**
	 * It will update a product details
	 * 
	 * @param pro
	 * @return
	 */
	public String updateItem(final Product pro) {
		return productExcelFile.updateItemInExcel("./src/main/resources/excel/product.xlsx", pro);
	}

}
