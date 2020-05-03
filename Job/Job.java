package com.Project3.Job;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.Project3.Beans.Coupon;
import com.Project3.DB.CompanyDBDAO;
import com.Project3.DB.CouponDBDAO;
import com.Project3.DB.CustomerDBDAO;
import com.Project3.Exceptions.couponWasNotFoundException;

@Component
public class Job implements Runnable{
	
	@Autowired
	protected CompanyDBDAO companyDB;
	
	@Autowired
	protected CouponDBDAO couponDB;
	
	@Autowired
	protected CustomerDBDAO customerDB;

	private boolean quit;
	
	@Override
	public void run() {

		while(!quit) {
			Calendar c1 = Calendar.getInstance();
			// get all coupons from DB
			// check expired coupons
			// delete expires coupons
			try {
				try {
					for (Coupon c : couponDB.getAllCoupons()) {
						if(c.getEndDate().after(c1.getTime())) {
							couponDB.deleteCoupon(c.getCouponId());
						}
						
					}
				} catch (couponWasNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Thread.sleep(1000*60*60*24);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

		}
	public void stopJob() {
		if(quit == true) {
			quit = false;
		}
}

}