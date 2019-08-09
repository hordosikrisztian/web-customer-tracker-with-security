package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerContoller {

	// Need to inject the customer service.
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model model) {
		// Get customers from the customer service.
		List<Customer> customers = customerService.getCustomers();

		// Add the customers to the model.
		model.addAttribute("customers", customers);

		return "list-customers";
	}

	@GetMapping("/search")
	public String searchCustomers(@RequestParam("searchName") String searchName, Model model) {
		// Search customers from the service.
		List<Customer> customers = customerService.searchCustomers(searchName);

		// Add customers to the model.
		model.addAttribute("customers", customers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		// Create model attribute to bind form data.
		Customer customer = new Customer();

		// Add customer to the model.
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
		// Get the customer from the service.
		Customer customer = customerService.getCustomer(id);

		// Set customer as a model attribute to pre-populate the form.
		model.addAttribute("customer", customer);

		// Send over to our form.
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		// Save the customer using our service.
		customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		// Delete the customer.
		customerService.deleteCustomer(id);

		return "redirect:/customer/list";
	}

}
