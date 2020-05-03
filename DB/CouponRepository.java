package com.Project3.DB;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Project3.Beans.Coupon;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
//	List<Coupon> findCouponsByCategory(CategoryType category);
//	List<Coupon> findCouponsByMaxPriceLessThen(double maxPrice);
//	List<Coupon> findCouponsByCompanyId(Company companyId);
}