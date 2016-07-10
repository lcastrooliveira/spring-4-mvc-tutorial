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

import java.util.ArrayList;
import java.util.List;

import org.mockito.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;


public class CustomerControllerTest implements SimpleCRUDTester {

	private MockMvc mockMvc;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerController customerController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Override
	@Test
	public void testList() throws Exception {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer());
		customers.add(new Customer());
		
		when(customerService.listAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(get("/customers"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customers"))
			.andExpect(model().attribute("customers", hasSize(2)));
	}

	@Override
	@Test
	public void testShow() throws Exception {
		Integer id = 1;
		when(customerService.getCustomerById(id)).thenReturn(new Customer());
		
		mockMvc.perform(get("/customer/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customer"))
			.andExpect(model().attribute("customer",instanceOf(Customer.class)));
	}

	@Override
	@Test
	public void testEdit() throws Exception {
		Integer id = 1;
		when(customerService.getCustomerById(id)).thenReturn(new Customer());
		
		mockMvc.perform(get("/customer/edit/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerForm"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}

	@Override
	@Test
	public void testNew() throws Exception {
		
		verifyZeroInteractions(customerService);
		
		mockMvc.perform(get("/customer/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("customer/customerForm"))
			.andExpect(model().attribute("customer", instanceOf(Customer.class)));
	}

	@Override
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		Integer id = 1;
		//firstName, lastName, email, phoneNumber, addressLine1, addressLine2, city, state, zipCode
		String firstName = "Lucas";
		String lastName = "Oliveira";
		String email = "lcastro.oliveira@gmail.com";
		String phoneNumber = "+553256487896";
		String addressLine1 = "Rua do Flamengo, 28";
		String addressLine2 = "Copacabana";
		String city = "Rio de Janeiro";
		String state = "Rio de Janeiro";
		String zipCode = "32482987";
		
		Customer returnCustomer = new Customer();
		returnCustomer.setId(id);
		returnCustomer.setAddressLine1(addressLine1);
		returnCustomer.setAddressLine2(addressLine2);
		returnCustomer.setCity(city);
		returnCustomer.setEmail(email);
		returnCustomer.setFirstName(firstName);
		returnCustomer.setLastName(lastName);
		returnCustomer.setPhoneNumber(phoneNumber);
		returnCustomer.setState(state);
		returnCustomer.setZipCode(zipCode);
		
		when(customerService.saveOrUpdate(Matchers.<Customer>any())).thenReturn(returnCustomer);
		
		mockMvc.perform(post("/customer")
			.param("id", "1")
			.param("firstName", firstName)
			.param("lastName", lastName)
			.param("email", email)
			.param("phoneNumber", phoneNumber)
			.param("addressLine1", addressLine1)
			.param("addressLine2", addressLine2)
			.param("city", city)
			.param("state", state)
			.param("zipCode", zipCode))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/customer/1"))
				.andExpect(model().attribute("customer", instanceOf(Customer.class)))			
				.andExpect(model().attribute("customer", hasProperty("id",is(id))))
				.andExpect(model().attribute("customer", hasProperty("firstName",is(firstName))))
				.andExpect(model().attribute("customer", hasProperty("lastName",is(lastName))))
				.andExpect(model().attribute("customer", hasProperty("email",is(email))))
				.andExpect(model().attribute("customer", hasProperty("phoneNumber",is(phoneNumber))))
				.andExpect(model().attribute("customer", hasProperty("addressLine1",is(addressLine1))))
				.andExpect(model().attribute("customer", hasProperty("addressLine2",is(addressLine2))))
				.andExpect(model().attribute("customer", hasProperty("city",is(city))))
				.andExpect(model().attribute("customer", hasProperty("state",is(state))))
				.andExpect(model().attribute("customer", hasProperty("zipCode",is(zipCode))));
		
		ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
		verify(customerService).saveOrUpdate(boundCustomer.capture());
		
		assertEquals(id, boundCustomer.getValue().getId());
		
		assertEquals(firstName, boundCustomer.getValue().getFirstName());
		assertEquals(lastName, boundCustomer.getValue().getLastName());
		assertEquals(email, boundCustomer.getValue().getEmail());
		
		assertEquals(phoneNumber, boundCustomer.getValue().getPhoneNumber());
		assertEquals(addressLine1, boundCustomer.getValue().getAddressLine1());
		assertEquals(addressLine2, boundCustomer.getValue().getAddressLine2());
		
		assertEquals(city, boundCustomer.getValue().getCity());
		assertEquals(state, boundCustomer.getValue().getState());
		assertEquals(zipCode, boundCustomer.getValue().getZipCode());
			
	}

	@Override
	@Test
	public void testDelete() throws Exception {
		
		Integer id = 1;
		mockMvc.perform(get("/customer/delete/1"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/customers"));
		
		verify(customerService,times(1)).deleteCustomer(id);
		
	}
	
	
	
}

