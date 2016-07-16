package guru.springframework.services.mapservices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;
import guru.springframework.services.interfaces.CustomerService;

@Service
@Profile("map")
public class CustomerServiceImpl implements CustomerService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private Map<Integer, Customer> customers;
	
	public void loadAllCustomers() {
		customers = new HashMap<>();
		EntityManager em = emf.createEntityManager();
		List<Customer> loadedCustomers = em.createQuery("from Customer", Customer.class).getResultList();
		for(Customer customer : loadedCustomers) {
			customers.put(customer.getId(), customer);
		}
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
