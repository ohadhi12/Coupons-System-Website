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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Project3.Beans.CategoryType;
import com.Project3.Beans.Coupon;
import com.Project3.Exceptions.cantUpdateCompanyIdException;
import com.Project3.Exceptions.cantUpdateCouponException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.companyCouponTitleDuplictionException;
import com.Project3.Exceptions.companyWasNotFoundException;
import com.Project3.Exceptions.couponWasNotFoundException;
import com.Project3.Facade.CompanyFacade;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("company")
public class CompanyController {

	@Autowired
	Map<String, Session> sessionsMap;
	
	
//	private boolean isTimeout(Session session, String token) {
//		if(session.getLastAccessed() - System.currentTimeMillis() >= 1000*10*60*24) {
//		sessionsMap.remove(token);
//		}
//		return true;
//}

	@PostMapping("/coupon/{token}")
	public ResponseEntity<Object> addCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
		Session session = sessionsMap.get(token);
		if (session != null) {
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				coupon.setCompany(compFacade.getCompanyDetails());
				compFacade.addCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (companyCouponTitleDuplictionException | companyWasNotFoundException
					| cantUpdateCompanyIdException | couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@PutMapping("/coupon/{token}")
	public ResponseEntity<Object> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				compFacade.updateCoupon(coupon);
				return ResponseEntity.ok(coupon);
			} catch (cantUpdateCouponException | companyWasNotFoundException | cantUpdateCompanyIdException e) {
				return ResponseEntity.badRequest().body(e.getMessage());

			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@DeleteMapping("/coupon/{token}/{couponId}")
	public ResponseEntity<Object> deleteCoupon(@PathVariable String token, @PathVariable int couponId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				compFacade.deleteCoupon(couponId);
				return ResponseEntity.ok("coupon has been deleted");
			} catch (couponWasNotFoundException | cantUpdateCustomerException | cantUpdateCompanyIdException e) {
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
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(compFacade.getOneCoupon(couponId));
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/coupon/{token}")
	public ResponseEntity<Object> getCompanyCoupons(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(compFacade.getCompanyCoupons());
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/coupon/category/{token}/{category}")
	public ResponseEntity<Object> getCompanyCouponsByCategory(@PathVariable String token, @PathVariable CategoryType category) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(compFacade.getCompanyCouponsByCategory(category));
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/coupon/maxPrice/{token}/{maxPrice}")
	public ResponseEntity<Object> getCompanyCouponsByPrice(@PathVariable String token, @PathVariable double maxPrice) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
			try {
				return ResponseEntity.ok(compFacade.getCompanyCouponsByPrice(maxPrice));
			} catch (couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/{token}")
	public ResponseEntity<Object> getCompanyDetails(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			CompanyFacade compFacade = (CompanyFacade) session.getFacade();
				try {
					return ResponseEntity.ok(compFacade.getCompanyDetails());
				} catch (companyWasNotFoundException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
			}
		}
	}
