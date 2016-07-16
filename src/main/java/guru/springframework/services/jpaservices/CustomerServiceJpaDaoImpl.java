package guru.springframework.services.jpaservices;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.domain.Customer;
import guru.springframework.services.interfaces.CustomerService;
import guru.springframework.services.security.EncryptionService;

@Service
@Profile("jpadao")
public class CustomerServiceJpaDaoImpl implements CustomerService {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@Autowired
	private EncryptionService encryptionService;
	
	@Override
	public List<Customer> listAllCustomers() {
		EntityManager em = emf.createEntityManager();
		return em.createQuery("from Customer",Customer.class).getResultList();
	}

	@Override
	public Customer getCustomerById(Integer id) {
		EntityManager em = emf.createEntityManager();
		return em.find(Customer.class, id);
	}

	@Override
	public Customer saveOrUpdate(Customer customer) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		if(customer.getUser() != null && customer.getUser().getPassword() != null) {
			customer.getUser().setEncryptedPassword(encryptionService.encryptString(customer.getUser().getPassword()));
		}
		Customer savedCustomer = em.merge(customer);
		em.getTransaction().commit();
		return savedCustomer;
	}

	@Override
	public void deleteCustomer(Integer id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.find(Customer.class, id));
		em.getTransaction().commit();
	}

	
	
}
