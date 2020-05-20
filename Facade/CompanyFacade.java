package com.Project3.Facade;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.Project3.Beans.CategoryType;
import com.Project3.Beans.Company;
import com.Project3.Beans.Coupon;
import com.Project3.Beans.Customer;
import com.Project3.Exceptions.cantUpdateCompanyIdException;
import com.Project3.Exceptions.cantUpdateCouponException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.companyCouponTitleDuplictionException;
import com.Project3.Exceptions.companyWasNotFoundException;
import com.Project3.Exceptions.couponWasNotFoundException;

@Service
public class CompanyFacade extends ClientFacade {
	private int companyId;
	
	

	// add coupon to company
	public void addCoupon(Coupon coupon) throws companyCouponTitleDuplictionException, companyWasNotFoundException, cantUpdateCompanyIdException, couponWasNotFoundException {
		List<Coupon> companyCoupons = couponDB.getAllCoupons();
		for (Coupon coupon2 : companyCoupons) {
			if (coupon2.getTitle().equals(coupon.getTitle()))
				throw new companyCouponTitleDuplictionException();
		}
		couponDB.addCoupon(coupon);
		Company comp = getCompanyDetails();
		comp.addCoupon(coupon);
		companyDB.updateCompany(comp);
	}

	// updating a coupon in a company
	public void updateCoupon(Coupon coupon) throws	cantUpdateCouponException, companyWasNotFoundException, cantUpdateCompanyIdException {
		List<Coupon> couponsToUpdate = companyDB.getCompanyById(companyId).getCoupons();
		for (Coupon coupon2 : couponsToUpdate) {
			if (coupon2.getCompany().getCompanyId() == coupon.getCompany().getCompanyId()) {
				throw new cantUpdateCouponException();
			}
		}
		couponDB.updateCoupon(coupon);
		Company comp = getCompanyDetails();
		companyDB.updateCompany(comp);
	}

	// deleting a coupon in a company
	public void deleteCoupon(int couponId)
			throws couponWasNotFoundException, cantUpdateCustomerException, cantUpdateCompanyIdException {

		Coupon couponToDelete = couponDB.getOneCoupon(couponId);
		Company comp = couponToDelete.getCompany();
		comp.removeCoupon(couponToDelete);
		companyDB.updateCompany(comp);

		List<Customer> customers = customerDB.getAllCustomers();
		for (Customer customer : customers) {
			customer.getCoupons().contains(couponToDelete);
			customer.getCoupons().remove(couponToDelete);
			customerDB.updateCustomer(customer);
		}
		couponDB.deleteCoupon(couponId);

	}
	
	public Coupon getOneCoupon(int couponId) throws couponWasNotFoundException {
		return couponDB.getOneCoupon(couponId);
	}

	// returning company coupons
	public List<Coupon> getCompanyCoupons() throws couponWasNotFoundException {
		List<Coupon> coup = new ArrayList<Coupon>();
		for (Coupon coupons : couponDB.getAllCoupons()) {
			if (coupons.getCompany().getCompanyId() == companyId)
				coup.add(coupons);
		}
		if (coup.isEmpty())
			return null;
		else {
			return coup;
		}
	}

	// returning company coupons by category
	public List<Coupon> getCompanyCouponsByCategory(CategoryType category) throws couponWasNotFoundException {
		List<Coupon> coup = new ArrayList<Coupon>();
		for (Coupon coupons : getCompanyCoupons()) {
			if (coupons.getCategory() == category) {
				coup.add(coupons);
			}
		}
		if (coup.isEmpty())
			return null;
		else {
			return coup;
		}
	}

	// returning company coupons by max price
	public List<Coupon> getCompanyCouponsByPrice(double maxPrice)throws couponWasNotFoundException  {
		List<Coupon> coup = new ArrayList<Coupon>();
		for (Coupon coupons : getCompanyCoupons()) {
			if (coupons.getPrice() <= maxPrice) {
				coup.add(coupons);
			}
		}
		if (coup.isEmpty())
			return null;
		else {
			return coup;
		}

	}

	// get company details
	public Company getCompanyDetails() throws companyWasNotFoundException {
		return companyDB.getCompanyById(companyId);

	}

	// company system login
	public boolean login(String email, String password) {
		Company temp = companyDB.findCompanyByEmailAndPassword(email, password);
		if (temp != null) {
			companyId = temp.getCompanyId();
			return true;
		}
		return false;
	}

}
