package guru.springframework.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import guru.springframework.services.interfaces.ProductService;
import guru.springframework.services.interfaces.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class UserServiceJpaDaoImplTest {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testSaveOfUser() throws Exception {
		User user = new User();
		user.setUsername("someusername");
		user.setPassword("mypassword");
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser != null;
		assert savedUser.getEncryptedPassword() != null;
		
		System.out.println("Encrypted Password");
		System.out.println(savedUser.getEncryptedPassword());
				
	}
	
	@Test
	public void testSaveOfUserWithCustomer() throws Exception {
		User user = new User();
		user.setUsername("someusername");
		user.setPassword("somePassword");
		
		Customer customer = new Customer();
		customer.setFirstName("Chevy");
		customer.setFirstName("Chase");
		
		user.setCustomer(customer);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCustomer() != null;
		assert savedUser.getCustomer().getId() != null;
	}
	
	@Test
	public void testAddCartToUser() throws Exception {
		User user = new User();
		
		user.setUsername("someusername");
		user.setPassword("mypassword");
		
		user.setCart(new Cart());
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCart() != null;
		assert savedUser.getCart().getId() != null;
	}
	
	@Test
	public void testAddCartToUserWithCartDetails() throws Exception {
		User user = new User();
		
		user.setUsername("someusername");
		user.setPassword("mypassword");
		
		user.setCart(new Cart());
		
		List<Product> storedProducts = (List<Product>) productService.listAll();
		
		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetail(cartItemOne);
		
		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(1));
		user.getCart().addCartDetail(cartItemTwo);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getId() != null;
		assert savedUser.getVersion() != null;
		assert savedUser.getCart() != null;
		assert savedUser.getCart().getId() != null;
		assert savedUser.getCart().getCartDetails().size() == 2;
	}
	
	@Test
	public void testAddAndRemoveCartToUserWithCartDetails() throws Exception {
		User user = new User();
		
		user.setUsername("someusername");
		user.setPassword("mypassword");
		
		user.setCart(new Cart());
		
		List<Product> storedProducts = (List<Product>) productService.listAll();
		
		CartDetail cartItemOne = new CartDetail();
		cartItemOne.setProduct(storedProducts.get(0));
		user.getCart().addCartDetail(cartItemOne);
		
		CartDetail cartItemTwo = new CartDetail();
		cartItemTwo.setProduct(storedProducts.get(1));
		user.getCart().addCartDetail(cartItemTwo);
		
		User savedUser = userService.saveOrUpdate(user);
		
		assert savedUser.getCart().getCartDetails().size() == 2;
		
		savedUser.getCart().removeCartDetail(savedUser.getCart().getCartDetails().get(0));
		
		userService.saveOrUpdate(savedUser);
		
		assert savedUser.getCart().getCartDetails().size() == 1;
		
	}
}
