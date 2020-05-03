package com.Project3.Facade;
import java.util.List;
import org.springframework.stereotype.Service;
import com.Project3.Beans.Company;
import com.Project3.Beans.Coupon;
import com.Project3.Beans.Customer;
import com.Project3.Exceptions.cantUpdateCompanyIdException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.companyExistsException;
import com.Project3.Exceptions.companyWasNotFoundException;
import com.Project3.Exceptions.couponWasNotFoundException;
import com.Project3.Exceptions.customerAlreadyExistsException;
import com.Project3.Exceptions.customerWasNotFoundException;

@Service
public class AdminFacade extends ClientFacade {

	// adding company to the data base
	public void addCompany(Company company) throws companyExistsException {
		List<Company> allCompanies = companyDB.getAllCompanies();
		for (Company company2 : allCompanies) {
			if (company2.getEmail().equals(company.getEmail()) || company2.getName().equals(company.getName())) {
				throw new companyExistsException();
			}
		}
		companyDB.addCompany(company);
	}

	// updating company to the data base
	public void updateCompany(Company company) throws cantUpdateCompanyIdException {
			companyDB.updateCompany(company);
	}

	// deleting company from the data base
	public void deleteCompany(int companyId) throws companyWasNotFoundException, cantUpdateCompanyIdException, cantUpdateCustomerException, customerWasNotFoundException, couponWasNotFoundException {

		List<Customer> allCustomers = customerDB.getAllCustomers();
//		List<Coupon> coupons =  couponDB.getAllCoupons();
		for (Customer customer : allCustomers) {
			for (int i = customer.getCoupons().size()-1; i>=0; i--) {
				Coupon coupon = customer.getCoupons().get(i);
				if(coupon.getCompany().getCompanyId() == companyId) {
					customer.removeCoupon(coupon);
				}
			}
			customerDB.updateCustomer(customer);
			Company companyToDelete = companyDB.getCompanyById(companyId);
			companyToDelete.getCoupons().clear();
			companyDB.updateCompany(companyToDelete);
		}
		companyDB.deleteCompany(companyId);
	}

	// returning all the companies
	public List<Company> getAllCompanies() throws companyWasNotFoundException {
		List<Company> allCompanies = companyDB.getAllCompanies();
		return allCompanies;
	}

	// returning company by id
	public Company getOneCompany(int companyId) throws companyWasNotFoundException {
		return companyDB.getCompanyById(companyId);
	}

	// adding a customer to the data base
	public void addCustomer(Customer customer) throws customerAlreadyExistsException {
		List<Customer> allCustomers = customerDB.getAllCustomers();
		for (Customer customer2 : allCustomers) {
			if (customer2.getEmail().equals(customer.getEmail())) {
				throw new customerAlreadyExistsException();
			}
		}
		customerDB.addCustomer(customer);
	}

	// updating a customer in the data base
	public void updateCustomer(Customer customer) throws cantUpdateCustomerException {
		customerDB.updateCustomer(customer);
	}

	// deleting a customer by id
	public void deleteCustomer(int customerId)
			throws customerWasNotFoundException, couponWasNotFoundException, cantUpdateCustomerException {
		Customer customerToDelete = customerDB.getCustomerById(customerId);
		customerToDelete.getCoupons().clear();
		customerDB.updateCustomer(customerToDelete);
		customerDB.deleteCustomer(customerId);
	}

	// returning all customers
	public List<Customer> getAllCustomers() throws customerWasNotFoundException {
		List<Customer> allCustomers = customerDB.getAllCustomers();
		return allCustomers;
	}

	// returning a customer by id
	public Customer getOneCustomer(int customerId) throws customerWasNotFoundException {
		return customerDB.getCustomerById(customerId);

	}

	// login the system
	public boolean login(String email, String password) {
		if (email.equals("admin@admin.com") && password.equals("admin")) {
			return true;
		}
		return false;
	}
}