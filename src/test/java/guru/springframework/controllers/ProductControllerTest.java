package guru.springframework.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;

public class ProductControllerTest {

	@Mock //mockito mock object
	private ProductService productService;
	
	@InjectMocks //setups controller, and injects mock objects into it.
	private ProductController productController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this); //initializes controller and mocks
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	public void testList() throws Exception {
		List<Product> products = new ArrayList<>();
		products.add(new Product());
		products.add(new Product());
		
		//specific  mockito interaction, tell stub to return list of products
		when(productService.listAllProducts()).thenReturn(products);
		
		mockMvc.perform(get("/products"))
			.andExpect(status().isOk())
			.andExpect(view().name("product/products"))
			.andExpect(model().attribute("products", hasSize(2)));
	}
	
	@Test
	public void testShow() throws Exception {
		Integer id = 1;
		
		//Tell mockito stub to return new product for ID 1
		when(productService.getProductById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/product/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("product/product"))
		.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testEdit() throws Exception {
		Integer id = 1;
		
		//Tell mockito stub to return new product for ID 1
		when(productService.getProductById(id)).thenReturn(new Product());
		
		mockMvc.perform(get("/product/edit/1"))
		.andExpect(status().isOk())
		.andExpect(view().name("product/productForm"))
		.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testNew() throws Exception {
		
		//should not call service
		verifyZeroInteractions(productService);
		
		mockMvc.perform(get("/product/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("product/productForm"))
		.andExpect(model().attribute("product", instanceOf(Product.class)));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		Integer id = 1;
		String description = "Test Description";
		BigDecimal price = new BigDecimal("12.00");
		String imageUrl = "example.com";
		
		Product returnProduct = new Product();
		returnProduct.setId(id);
		returnProduct.setDescription(description);
		returnProduct.setPrice(price);
		returnProduct.setImageUrl(imageUrl);
		
		when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(returnProduct);
		
		mockMvc.perform(post("/product")
				.param("id", "1")
				.param("description", description)
				.param("price","12.00")
				.param("imageUrl","example.com"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/product/product/1"))
					.andExpect(model().attribute("product", instanceOf(Product.class)))
					.andExpect(model().attribute("product", hasProperty("id", is(id))))
					.andExpect(model().attribute("product", hasProperty("description", is(description))))
					.andExpect(model().attribute("product", hasProperty("price", is(price))))
					.andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));
		
		//verify properties of bound object
		ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
		verify(productService).saveOrUpdate(boundProduct.capture());
		
		assertEquals(id, boundProduct.getValue().getId());
		assertEquals(description, boundProduct.getValue().getDescription());
		assertEquals(price, boundProduct.getValue().getPrice());
		assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
		
	}
	
	@Test
	public void testDelete() throws Exception {
		
		Integer id = 1;
		mockMvc.perform(get("/product/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:product/products"));
		
		verify(productService,times(1)).deleteProduct(id);
	}
	
}
