package com.Project3.Web;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Project3.Beans.CategoryType;
import com.Project3.Beans.Coupon;
import com.Project3.Exceptions.cantUpdateCouponException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.couponPurchasedException;
import com.Project3.Exceptions.couponWasNotFoundException;
import com.Project3.Exceptions.customerWasNotFoundException;
import com.Project3.Exceptions.expiredCouponException;
import com.Project3.Exceptions.noCouponsException;
import com.Project3.Facade.CustomerFacade;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("customer")
public class CustomerController {

	@Autowired
	Map<String, Session> sessionsMap;

	
//	private boolean isTimeout(Session session) {
//		if(session.getLastAccessed() - System.currentTimeMillis() >= 1000*60*30) {
//		return false;
//	}else {
//			return true;
//		}
//}
	
	
	@PostMapping("/coupon/{token}")
	public ResponseEntity<Object> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				custFacade.purchaseCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (expiredCouponException | noCouponsException | customerWasNotFoundException
					| couponPurchasedException | cantUpdateCustomerException | cantUpdateCouponException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}

		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
		}
	

	@DeleteMapping("/coupon/{token}/{couponId}")
	public ResponseEntity<Object> deleteCouponPurchase(@PathVariable String token, @PathVariable int couponId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				custFacade.deleteCouponPurchase(couponId);
				return ResponseEntity.ok("coupon purchase deleted!");
			} catch (cantUpdateCustomerException | couponWasNotFoundException | customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
		}
	

	@GetMapping("/coupon/{token}/{couponId}")
	public ResponseEntity<Object> getOneCoupon(@PathVariable String token, @PathVariable int couponId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getOneCoupon(couponId));
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	

	@GetMapping("/coupon/{token}")
	public ResponseEntity<Object> getCustomerCoupons(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getCustomerCoupons());
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
		}
	

	@GetMapping("/coupon/category/{token}/{category}")
	public ResponseEntity<Object> getCustomerCouponsByCategory(@PathVariable String token,
			@PathVariable CategoryType category) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getCustomerCouponsByCategory(category));
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	

	@GetMapping("/coupon/maxPrice/{token}/{maxPrice}")
	public ResponseEntity<Object> getCustomerCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getCustomerCouponsByPrice(maxPrice));
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/{token}")
	public ResponseEntity<Object> getCustomerDetails(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getCustomerDetails());
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	@GetMapping("/coupons/{token}")
	public ResponseEntity<Object> getAllCoupons(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CustomerFacade custFacade = (CustomerFacade) session.getFacade();
			try {
				return ResponseEntity.ok(custFacade.getAllCoupons());
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}	
}