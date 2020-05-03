package com.Project3.DB;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.Project3.Beans.Coupon;
import com.Project3.Exceptions.cantUpdateCouponException;
import com.Project3.Exceptions.couponWasNotFoundException;
@Repository
@Component
public class CouponDBDAO {
	@Autowired
	private CouponRepository couponRepo;
	
	public void addCoupon(Coupon coupon) {
		couponRepo.save(coupon);
	}
	
	public void deleteCoupon(int couponId) {
		couponRepo.deleteById(couponId);
	}
	
	public void updateCoupon(Coupon coupon) throws cantUpdateCouponException {
		if(couponRepo.existsById(coupon.getCouponId())) {
			couponRepo.save(coupon);
			}else {
				throw new cantUpdateCouponException();
			}
	}
	
	public List<Coupon> getAllCoupons() throws couponWasNotFoundException{
		return couponRepo.findAll();
		
	}
	public Coupon getOneCoupon(int couponId) throws couponWasNotFoundException {
		Optional<Coupon> opt = couponRepo.findById(couponId);
		if(opt.isPresent()) 
			return opt.get();
		 throw new couponWasNotFoundException();
	}
	
//	public 	List<Coupon> findCouponsByCategory(CategoryType category) {
//		return couponRepo.findCouponsByCategory(category);
//	}
//
//	public 	List<Coupon> findCouponsByMaxPriceLessThen(double maxPrice) {
//		return couponRepo.findCouponsByMaxPriceLessThen(maxPrice);
//	}
//	
//	public 	List<Coupon> findCouponsByCompanyId(Company companyId) {
//		return couponRepo.findCouponsByCompanyId(companyId);
//	}
}