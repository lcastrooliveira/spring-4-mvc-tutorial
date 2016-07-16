package guru.springframework.services;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Product;
import guru.springframework.services.interfaces.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class ProductServiceJpaDaoImplTest {

	private ProductService productService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Test
	public void testListMethod() throws Exception {
		List<Product> products = productService.listAll();
		
		assert products.size() == 5;
	}
	
	@Test
	public void testGetProductById() throws Exception {
		Product product1 = productService.findById(1);
		assert product1 != null;
		assert product1.getId() == 1;
		
		assert product1.getDescription().equals("Product 1");
		assert product1.getPrice().equals(new BigDecimal("12.99"));
		assert product1.getImageUrl().equals("http://example.com/product1");
		
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		Product product6 = new Product();
		//product6.setId(6);
		product6.setDescription("Product 6");
		product6.setPrice(new BigDecimal("100.50"));
		product6.setImageUrl("http://example.com/product6");
		
		Product savedProduct = productService.saveOrUpdate(product6);
		
		assert savedProduct != null;
		assert savedProduct.getId() == 6;
		
		assert savedProduct.getDescription().equals("Product 6");
		assert savedProduct.getPrice().equals(new BigDecimal("100.50"));
		assert savedProduct.getImageUrl().equals("http://example.com/product6");
		
	}
	
	@Test
	public void testDeleteProduct() throws Exception {
		productService.delete(1);
		assert productService.findById(1) == null;
	}
}
