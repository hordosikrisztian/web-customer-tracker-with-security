package com.luv2code.springdemo.dao;

import java.util.List;

import com.luv2code.springdemo.entity.Customer;

public interface CustomerDAO {

	List<Customer> getCustomers();

	Customer getCustomer(int id);

	List<Customer> searchCustomers(String searchName);

	void saveCustomer(Customer customer);

	void deleteCustomer(int id);

}
