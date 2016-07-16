package guru.springframework.bootstrap;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Address;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartDetail;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Order;
import guru.springframework.domain.OrderLine;
import guru.springframework.domain.Product;
import guru.springframework.domain.User;
import guru.springframework.domain.security.Role;
import guru.springframework.enums.OrderStatus;
import guru.springframework.services.interfaces.CustomerService;
import guru.springframework.services.interfaces.OrderService;
import guru.springframework.services.interfaces.ProductService;
import guru.springframework.services.interfaces.RoleService;
import guru.springframework.services.interfaces.UserService;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private ProductService productService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		loadCustomers();
		loadProducts();
		loadOrders();
		loadRoles();
		assignUsersToDefaultRoles();
	}

	private void assignUsersToDefaultRoles() {
		List<Role> roles = roleService.listAll();
		List<User> users = userService.listAll();
		
		roles.forEach(role -> {
			if(role.getRole().equalsIgnoreCase("CUSTOMER")) {
				users.forEach(user -> {
					user.addRole(role);
					userService.saveOrUpdate(user);
				});
			}
		});
	}

	private void loadRoles() {
		Role role = new Role();
		role.setRole("CUSTOMER");
		roleService.saveOrUpdate(role);
	}

	private void loadProducts() {
		Product product1 = new Product();
		product1.setId(1);
		product1.setDescription("Product 1");
		product1.setPrice(new BigDecimal("12.99"));
		product1.setImageUrl("http://example.com/product1");

		productService.saveOrUpdate(product1);

		Product product2 = new Product();
		product2.setId(2);
		product2.setDescription("Product 2");
		product2.setPrice(new BigDecimal("14.99"));
		product2.setImageUrl("http://example.com/product2");

		productService.saveOrUpdate(product2);

		Product product3 = new Product();
		product3.setId(3);
		product3.setDescription("Product 3");
		product3.setPrice(new BigDecimal("34.99"));
		product3.setImageUrl("http://example.com/product3");

		productService.saveOrUpdate(product3);

		Product product4 = new Product();
		product4.setId(4);
		product4.setDescription("Product 4");
		product4.setPrice(new BigDecimal("44.99"));
		product4.setImageUrl("http://example.com/product4");

		productService.saveOrUpdate(product4);

		Product product5 = new Product();
		product5.setId(5);
		product5.setDescription("Product 5");
		product5.setPrice(new BigDecimal("25.99"));
		product5.setImageUrl("http://example.com/product5");

		productService.saveOrUpdate(product5);
	}
	
	private void loadCustomers() {

		
		User user1 = new User();
		user1.setUsername("moskito");
		user1.setPassword("123456");
		
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setBillingAddress(new Address());
		customer1.getBillingAddress().setAddressLine1("Rua A, 123");
		customer1.getBillingAddress().setAddressLine2("Jardim dos Cachorros");
		customer1.getBillingAddress().setCity("Foz do Iguaçu");
		customer1.setEmail("fernando@gmail.com");
		customer1.setFirstName("Fernando");
		customer1.setLastName("Fagundes");
		customer1.setPhoneNumber("+554152368987");
		customer1.getBillingAddress().setState("Paraná");
		customer1.getBillingAddress().setZipCode("85847985");
		
		user1.setCustomer(customer1);
		userService.saveOrUpdate(user1);
		
		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setBillingAddress(new Address());
		customer2.getBillingAddress().setAddressLine1("Rua B, 456");
		customer2.getBillingAddress().setAddressLine2("Jardim dos Gatos");
		customer2.getBillingAddress().setCity("Cascavel");
		customer2.setEmail("luciana@gmail.com");
		customer2.setFirstName("Luciana");
		customer2.setLastName("Teixeira");
		customer2.setPhoneNumber("+554563215478");
		customer2.getBillingAddress().setState("Paraná");
		customer2.getBillingAddress().setZipCode("85847325");
		
		customer2.setUser(new User());
		customer2.getUser().setCustomer(customer2);
		customer2.getUser().setUsername("lucianaa");
		customer2.getUser().setPassword("987654");
		
		customerService.saveOrUpdate(customer2);
		
		Customer customer3 = new Customer();
		customer3.setId(3);
		customer3.setBillingAddress(new Address());
		customer3.getBillingAddress().setAddressLine1("Rua C, 789");
		customer3.getBillingAddress().setAddressLine2("Jardim dos Coelhos");
		customer3.getBillingAddress().setCity("Recife");
		customer3.setEmail("Alberto@gmail.com");
		customer3.setFirstName("Alberto");
		customer3.setLastName("Barroso");
		customer3.setPhoneNumber("+556523215852");
		customer3.getBillingAddress().setState("Pernambuco");
		customer3.getBillingAddress().setZipCode("45632456");
		
		customer3.setUser(new User());
		customer3.getUser().setCustomer(customer3);
		customer3.getUser().setUsername("bertinho");
		customer3.getUser().setPassword("876543");
		
		
		customerService.saveOrUpdate(customer3);
	}
	
	private void loadOrders() {
		
		Product aProduct = productService.findById(1);
		
		Customer aCustomer = customerService.getCustomerById(2);
		aCustomer.getUser().setCart(new Cart());
		aCustomer.getUser().getCart().setUser(aCustomer.getUser());
		//customerService.saveOrUpdate(aCustomer);
		
		CartDetail detail = new CartDetail();
		detail.setProduct(aProduct);
		detail.setQuantity(5);		
		aCustomer.getUser().getCart().addCartDetail(detail);
		
		aCustomer = customerService.saveOrUpdate(aCustomer);
		
		Order order = new Order();
		order.setCustomer(aCustomer);
		order.setOrderStatus(OrderStatus.NEW);
		order.setShippingAddress(aCustomer.getBillingAddress());
		
		OrderLine orderLine = new OrderLine();
		orderLine.setProduct(aProduct);
		orderLine.setQuantity(7);
		
		order.addOrderLineToOrder(orderLine);
		
		orderService.saveOrUpdate(order);
	}
}

