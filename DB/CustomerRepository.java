package com.Project3.DB;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Project3.Beans.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Customer findCustomerByEmailAndPassword(String email, String password);
	
//	Customer findCustomerCouponsByCustomerId(int customerId);
	
}