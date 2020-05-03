package com.Project3.DB;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.Project3.Beans.Customer;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.customerWasNotFoundException;
@Repository
@Component
public class CustomerDBDAO {
	@Autowired
	private CustomerRepository customerRepo;
	
	public void addCustomer(Customer customer) {
		customerRepo.save(customer);
	}
	public void deleteCustomer(int customerId) {
		customerRepo.deleteById(customerId);
	}
	public void updateCustomer(Customer customer) throws cantUpdateCustomerException {
		if(customerRepo.existsById(customer.getCustomerId())) {
			customerRepo.save(customer);
			}else {
				throw new cantUpdateCustomerException();
			}
	}
	
	public List<Customer> getAllCustomers(){
		return customerRepo.findAll();
	}
	
	public Customer getCustomerById(int customerId) throws customerWasNotFoundException {
		Optional<Customer> opt = customerRepo.findById(customerId);
		if(opt.isPresent()) 
			return opt.get();
		 throw new customerWasNotFoundException();
	}
	
	public boolean isCustomerExists(String email, String password) throws customerWasNotFoundException {
		List<Customer> allCustomers= (List<Customer>) getAllCustomers();
		for (Customer c : allCustomers) {
			if (c.getEmail().equals(allCustomers) && c.getPassword().equals(allCustomers))
				throw new customerWasNotFoundException();
}
		return true;
	}
	public Customer findCustomerByEmailAndPassword(String email, String password) {
		return customerRepo.findCustomerByEmailAndPassword(email, password);
	}
		}