package guru.springframework.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<Integer, Customer> customers;
	
	public CustomerServiceImpl() {
		loadAllCustomers();
	}
	
	public void loadAllCustomers() {
		customers = new HashMap<>();
		
		Customer customer1 = new Customer();
		customer1.setId(1);
		customer1.setAddressLine1("Rua A, 123");
		customer1.setAddressLine2("Jardim dos Cachorros");
		customer1.setCity("Foz do Iguaçu");
		customer1.setEmail("fernando@gmail.com");
		customer1.setFirstName("Fernando");
		customer1.setLastName("Fagundes");
		customer1.setPhoneNumber("+554152368987");
		customer1.setState("Paraná");
		customer1.setZipCode("85847985");
		
		customers.put(1, customer1);
		
		Customer customer2 = new Customer();
		customer2.setId(2);
		customer2.setAddressLine1("Rua B, 456");
		customer2.setAddressLine2("Jardim dos Gatos");
		customer2.setCity("Cascavel");
		customer2.setEmail("luciana@gmail.com");
		customer2.setFirstName("Luciana");
		customer2.setLastName("Teixeira");
		customer2.setPhoneNumber("+554563215478");
		customer2.setState("Paraná");
		customer2.setZipCode("85847325");
		
		customers.put(2, customer2);
		
		Customer customer3 = new Customer();
		customer3.setId(3);
		customer3.setAddressLine1("Rua C, 789");
		customer3.setAddressLine2("Jardim dos Coelhos");
		customer3.setCity("Recife");
		customer3.setEmail("Alberto@gmail.com");
		customer3.setFirstName("Alberto");
		customer3.setLastName("Barroso");
		customer3.setPhoneNumber("+556523215852");
		customer3.setState("Pernambuco");
		customer3.setZipCode("45632456");
		
		customers.put(3, customer3);
		
	}
	
	@Override
	public List<Customer> listAllCustomers() {
		return new ArrayList<Customer>(customers.values());
	}

	@Override
	public Customer getCustomerById(Integer id) {
		return customers.get(id);
	}

	@Override
	public Customer saveOrUpdate(Customer customer) {
		if(customer != null) {
			if(customer.getId() == null) {
				customer.setId(getNextKey());
			}
			customers.put(customer.getId(), customer);
			return customer;
		} else {
			throw new RuntimeException("Product cannot be nill");
		}
	}
	
	private Integer getNextKey() {
		return Collections.max(customers.keySet()) + 1;
	}

	@Override
	public void deleteCustomer(Integer id) {
		customers.remove(id);
	}
	
}
