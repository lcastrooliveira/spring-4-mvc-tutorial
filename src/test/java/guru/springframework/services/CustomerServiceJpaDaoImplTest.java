package guru.springframework.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import guru.springframework.config.JpaIntegrationConfig;
import guru.springframework.domain.Address;
import guru.springframework.domain.Customer;
import guru.springframework.domain.User;
import guru.springframework.services.interfaces.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(JpaIntegrationConfig.class)
@ActiveProfiles({"jpadao"})
public class CustomerServiceJpaDaoImplTest {

	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testListMethod() throws Exception {
		List<Customer> customers = customerService.listAllCustomers();
		
		assert customers.size() == 3;
	}
	
	@Test
	public void testGetCustomerById() throws Exception {
		Customer customer = customerService.getCustomerById(1);
		
		assert customer != null;
		assert customer.getId() == 1;
		
		assert customer.getBillingAddress().getAddressLine1().equals("Rua A, 123");
		assert customer.getBillingAddress().equals("Jardim dos Cachorros");
		assert customer.getBillingAddress().equals("Foz do Iguaçu");
		assert customer.getEmail().equals("fernando@gmail.com");
		assert customer.getFirstName().equals("Fernando");
		assert customer.getLastName().equals("Fagundes");
		assert customer.getPhoneNumber().equals("+554152368987");
		assert customer.getBillingAddress().getState().equals("Paraná");
		assert customer.getBillingAddress().getZipCode().equals("85847985");
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		Customer customer2 = new Customer();
		customer2.setBillingAddress(new Address());
		customer2.getBillingAddress().setAddressLine1("Rua L, 74581");
		customer2.getBillingAddress().setAddressLine2("Jardim dos Macacos");
		customer2.getBillingAddress().setCity("Londrina");
		customer2.setEmail("marcelofanfarrao@gmail.com");
		customer2.setFirstName("Marcelo");
		customer2.setLastName("Fanfarrao");
		customer2.setPhoneNumber("+554556315445");
		customer2.getBillingAddress().setState("Paraná");
		customer2.getBillingAddress().setZipCode("86957325");
		
		Customer savedCustomer = customerService.saveOrUpdate(customer2);
		
		assert savedCustomer != null;
		assert savedCustomer.getId() == 1;
		
		assert savedCustomer.getBillingAddress().getAddressLine1().equals("Rua L, 74581");
		assert savedCustomer.getBillingAddress().getAddressLine2().equals("Jardim dos Macacos");
		assert savedCustomer.getBillingAddress().getCity().equals("Londrina");
		assert savedCustomer.getEmail().equals("marcelofanfarrao@gmail.com");
		assert savedCustomer.getFirstName().equals("Marcelo");
		assert savedCustomer.getLastName().equals("Fanfarrao");
		assert savedCustomer.getPhoneNumber().equals("+554556315445");
		assert savedCustomer.getBillingAddress().getState().equals("Paraná");
		assert savedCustomer.getBillingAddress().getZipCode().equals("86957325");
	}
	
	@Test
	public void testCustomerDelete() throws Exception {
		customerService.deleteCustomer(1);
		assert customerService.getCustomerById(1) == null; 
	}
	
	@Test
	public void testSaveWithUser() {
		Customer customer = new Customer();
		User user = new User();
		
		customer.setUser(user);
		user.setUsername("darthvader");
		user.setPassword("deathstar");
		
		Customer savedCustomer = customerService.saveOrUpdate(customer);
		assert savedCustomer.getUser().getId() != null;
	}
}
