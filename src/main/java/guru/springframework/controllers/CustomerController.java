package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import guru.springframework.domain.Customer;
import guru.springframework.services.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/customers")
	public String listCustomers(Model model) {
		model.addAttribute("customers",customerService.listAllCustomers());
		return "customer/customers";
	}
	
	@RequestMapping("/customer/{id}")
	public String getCustomer(@PathVariable Integer id, Model model) {
		model.addAttribute("customer", customerService.getCustomerById(id));
		return "customer/customer";
	}
	
	@RequestMapping("/customer/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("customer",customerService.getCustomerById(id));
		return "customer/customerForm";
	}
	
	@RequestMapping("/customer/new")
	public String newCustomer(Model model) {
		model.addAttribute("customer", new Customer());
		return "customer/customerForm";
	}
	
	@RequestMapping(value="/customer", method=RequestMethod.POST)
	public String saveOrUpdate(Customer customer) {
		Customer savedCustomer = customerService.saveOrUpdate(customer);
		return "redirect:/customer/"+savedCustomer.getId();
	}
	
	@RequestMapping("/customer/delete/{id}")
	public String delete(@PathVariable Integer id) {
		customerService.deleteCustomer(id);
		return "redirect:/customers";
	}
	
	
}
