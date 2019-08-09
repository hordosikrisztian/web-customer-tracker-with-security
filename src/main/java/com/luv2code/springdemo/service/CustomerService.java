package com.luv2code.springdemo.service;

import java.util.List;

import com.luv2code.springdemo.entity.Customer;

public interface CustomerService {

	List<Customer> getCustomers();

	Customer getCustomer(int id);

	List<Customer> searchCustomers(String searchName);

	void saveCustomer(Customer customer);

	void deleteCustomer(int id);

}
