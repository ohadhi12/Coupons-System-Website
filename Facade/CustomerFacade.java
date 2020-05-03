package com.Project3.Facade;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.Project3.Beans.CategoryType;
import com.Project3.Beans.Coupon;
import com.Project3.Beans.Customer;
import com.Project3.Exceptions.cantUpdateCouponException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.couponPurchasedException;
import com.Project3.Exceptions.couponWasNotFoundException;
import com.Project3.Exceptions.customerWasNotFoundException;
import com.Project3.Exceptions.expiredCouponException;
import com.Project3.Exceptions.noCouponsException;
@Service
public class CustomerFacade extends ClientFacade {
	private int customerId;
	

	// purchase coupons by customers
	public void purchaseCoupon(Coupon coupon)
			throws expiredCouponException, noCouponsException, customerWasNotFoundException, couponPurchasedException,
			cantUpdateCustomerException, cantUpdateCouponException {
		// קופון פג תוקף
		System.out.println(coupon);
		if (coupon.getEndDate().getTime()<System.currentTimeMillis()) {
			throw new expiredCouponException();
		}

		// אין קופונים במלאי - קופון אזל
		if (coupon.getAmount() == 0) {
			throw new noCouponsException();
		}

		// לקוח קנה כבר!
		List<Coupon> purchasedCoupons = (List<Coupon>) getCustomerCoupons();
		for (Coupon coupon1 : purchasedCoupons) {
			if (coupon1.getCouponId() == coupon.getCouponId()) {
				throw new couponPurchasedException();
			}
		}

		// everything is ok! buy the coupon
		coupon.setAmount(coupon.getAmount() - 1);
		Customer c = customerDB.getCustomerById(customerId);
		c.getCoupons().add(coupon);
		customerDB.updateCustomer(c);
		couponDB.updateCoupon(coupon);

	}

	// deleting customer purchase
	public void deleteCouponPurchase(int couponId)
			throws cantUpdateCustomerException, couponWasNotFoundException, customerWasNotFoundException {
		Customer c = customerDB.getCustomerById(customerId);
		c.getCoupons().remove(couponDB.getOneCoupon(couponId));
		customerDB.updateCustomer(c);

	}
	
	public Coupon getOneCoupon(int couponId) throws couponWasNotFoundException {
		return couponDB.getOneCoupon(couponId);
	}

	// return customer coupons
	public List<Coupon> getCustomerCoupons() throws customerWasNotFoundException {
		Customer c = customerDB.getCustomerById(customerId);
		c.getCoupons();
		return c.getCoupons();

	}

	// return customer coupons by category
	public List<Coupon> getCustomerCouponsByCategory(CategoryType category) throws customerWasNotFoundException {
		List<Coupon> coup = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getCategory() == category) {
				coup.add(coupon);
			}
		}
		if (coup.isEmpty())
			return null;
		else {
			return coup;
		}

	}

	// return customer coupons by max price
	public List<Coupon> getCustomerCouponsByPrice(double maxPrice) throws customerWasNotFoundException {
		List<Coupon> coup = new ArrayList<Coupon>();
		for (Coupon coupon : getCustomerCoupons()) {
			if (coupon.getPrice() <= maxPrice) {
				coup.add(coupon);
			}
		}
		if (coup.isEmpty())
			return null;
		else {
			return coup;
		}
	}

	// return customer details
	public Customer getCustomerDetails() throws customerWasNotFoundException {
		return customerDB.getCustomerById(customerId);
	}
	
	public List <Coupon> getAllCoupons() throws couponWasNotFoundException{
		return couponDB.getAllCoupons();
	}
	
	
	

	// customer system login
	public boolean login(String email, String password) {
		Customer temp = customerDB.findCustomerByEmailAndPassword(email, password);
		if (temp != null) {
			customerId = temp.getCustomerId();
			return true;
		}
		return false;
	}
}