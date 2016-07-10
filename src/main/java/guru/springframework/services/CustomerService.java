package guru.springframework.services;

import java.util.List;

import guru.springframework.domain.Customer;

public interface CustomerService {

	List<Customer> listAllCustomers();

	Customer getCustomerById(Integer id);

	Customer saveOrUpdate(Customer customer);

	void deleteCustomer(Integer id);

}
