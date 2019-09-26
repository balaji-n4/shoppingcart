package com.cts.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.model.Order;
import com.cts.model.Product;
import com.cts.repository.ProductDetailssRepo;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsServiceTest {
	
	@InjectMocks
	ProductDetailsService productDetailsService;
	
	@Mock
	ProductDetailssRepo repo;
	

	@Test
	public void addItemTest(){
		Product pro = new Product();
		pro.setPrice("234.56");
		pro.setProdId("B345");
		pro.setProdName("Bottle");
		when(repo.addItem(pro)).thenReturn("Item Added Successfully");
		assertEquals("Item Added Successfully", productDetailsService.addItem(pro));
	}
	
	@Test
	public void removeItemTest(){
		when(repo.removeItem("B345")).thenReturn("Item deleted Successfully");
		assertEquals("Item deleted Successfully", productDetailsService.removeItem("B345"));
	}
	
	@Test
	public void getAllProductsTest(){
		List<Product> products = new ArrayList<>();
		Product p = new Product();
		p.setProdId("G23423");
		Product p1 = new Product();
		p1.setProdId("Y23423");
		products.add(p1);
		products.add(p);
		when(repo.getAllProducts()).thenReturn(products);
		assertNotNull(productDetailsService.getAllProducts());
	}
	
	@Test
	public void getProductByIdTest(){
		Product p = new Product();
		p.setProdId("G23423");
		when(repo.getProductById("G23423")).thenReturn(p);
		assertEquals(p.getProdId(), productDetailsService.getProductById("G23423").getProdId());
		assertEquals(p.getPrice() , productDetailsService.getProductById("G23423").getPrice());
		assertEquals(p.getProdName(), productDetailsService.getProductById("G23423").getProdName());
		
	}
	
	@Test
	public void updateItemTest(){
		Product pro = new Product();
		pro.setPrice("234.56");
		pro.setProdId("B34556");
		pro.setProdName("Bottle");
		when(repo.updateItem(pro)).thenReturn("Product Updated Successfully,Product Id: B34556");
		assertEquals("Product Updated Successfully,Product Id: B34556", productDetailsService.addItem(pro));
	}
}
