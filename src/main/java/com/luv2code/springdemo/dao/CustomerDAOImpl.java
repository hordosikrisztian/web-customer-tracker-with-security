package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// Need to inject the session factory.
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		// Get the current Hibernate session.
		Session session = sessionFactory.getCurrentSession();

		// Create a query, sort by last name.
		Query<Customer> query = session.createQuery("FROM Customer c "
												  + "ORDER BY c.lastName", Customer.class);

		// Execute query and get result list.
		List<Customer> customers = query.getResultList();

		// Return the results.
		return customers;
	}

	@Override
	public Customer getCustomer(int id) {
		// Get the current Hibernate session.
		Session session = sessionFactory.getCurrentSession();

		// Now retrieve/read from database using the primary key.
		Customer customer = session.get(Customer.class, id);

		return customer;
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		Session session = sessionFactory.getCurrentSession();

		Query<Customer> query = null;

		// Only search by name if searchName is not empty
		if (searchName != null && searchName.trim().length() > 0) {
			// Search for firstName or lastName, case insensitive.
			query = session.createQuery("FROM Customer c "
									  + "WHERE lower(c.firstName) LIKE :name OR lower(c.lastName) LIKE :name", Customer.class)
						   .setParameter("name", "%" + searchName.toLowerCase() + "%");
		} else {
			// searchName is empty, so just get all customers.
			query = session.createQuery("FROM Customer", Customer.class);
		}

		// Execute query and get result list.
		List<Customer> customers = query.getResultList();

		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		// Get current Hibernate session.
		Session session = sessionFactory.getCurrentSession();

		// Save/update the customer.
		session.saveOrUpdate(customer);
	}

	@Override
	public void deleteCustomer(int id) {
		// Get current Hibernate session.
		Session session = sessionFactory.getCurrentSession();

		// Delete object with the primary key.
		session.createQuery("DELETE FROM Customer "
						  + "WHERE id = :id")
			   .setParameter("id", id)
			   .executeUpdate();
	}

}
