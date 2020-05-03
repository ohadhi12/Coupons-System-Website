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
import com.Project3.Beans.Company;
import com.Project3.Beans.Customer;
import com.Project3.Exceptions.cantUpdateCompanyIdException;
import com.Project3.Exceptions.cantUpdateCustomerException;
import com.Project3.Exceptions.companyExistsException;
import com.Project3.Exceptions.companyWasNotFoundException;
import com.Project3.Exceptions.couponWasNotFoundException;
import com.Project3.Exceptions.customerAlreadyExistsException;
import com.Project3.Exceptions.customerWasNotFoundException;
import com.Project3.Facade.AdminFacade;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("admin")
public class AdminController {

	@Autowired
	Map<String, Session> sessionsMap;

	private boolean isTimeout(Session session, String token) {
		long now = System.currentTimeMillis();
		long delta = 1000*30;
		while(session!=null) 
		if(now - session.getLastAccessed() >= delta)
		sessionsMap.remove(token);
		
		return false;
}
	
	@PostMapping("/company/{token}")
	public ResponseEntity<Object> addCompany(@PathVariable String token, @RequestBody Company company) {
		Session session = sessionsMap.get(token);
		isTimeout(session, token);
		if (session != null) {
			 session.setLastAccessed(System.currentTimeMillis());
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.addCompany(company);
				return ResponseEntity.ok(company);
			} catch (companyExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@PutMapping("/company/{token}")
	public ResponseEntity<Object> updateCompany(@PathVariable String token, @RequestBody Company company) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.updateCompany(company);
				return ResponseEntity.ok(company);
			} catch (cantUpdateCompanyIdException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@DeleteMapping("/company/{token}/{companyId}")
	public ResponseEntity<Object> deleteCompany(@PathVariable String token, @PathVariable int companyId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.deleteCompany(companyId);
				return ResponseEntity.ok("company has been deleted!");
			} catch (companyWasNotFoundException | cantUpdateCompanyIdException | cantUpdateCustomerException
					| customerWasNotFoundException | couponWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/company/{token}")
	public ResponseEntity<Object> getAllCompanies(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(admnFacade.getAllCompanies());
			} catch (companyWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/company/{token}/{companyId}")
	public ResponseEntity<Object> getOneCompany(@PathVariable String token, @PathVariable int companyId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(admnFacade.getOneCompany(companyId));
			} catch (companyWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@PostMapping("/customer/{token}")
	public ResponseEntity<Object> addCustomer(@PathVariable String token, @RequestBody Customer customer) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.addCustomer(customer);
				return ResponseEntity.ok(customer);
			} catch (customerAlreadyExistsException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@PutMapping("/customer/{token}")
	public ResponseEntity<Object> updateCustomer(@PathVariable String token, @RequestBody Customer customer) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.updateCustomer(customer);
				return ResponseEntity.ok(customer);
			} catch (cantUpdateCustomerException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	

	@DeleteMapping("/customer/{token}/{customerId}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable String token, @PathVariable int customerId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				admnFacade.deleteCustomer(customerId);
				return ResponseEntity.ok("customer has been deleted");
			} catch (customerWasNotFoundException | couponWasNotFoundException | cantUpdateCustomerException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	

	@GetMapping("/customer/{token}")
	public ResponseEntity<Object> getAllCustomers(@PathVariable String token) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(admnFacade.getAllCustomers());
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
	
	@GetMapping("/customer/{token}/{customerId}")
	public ResponseEntity<Object> getOneCustomer(@PathVariable String token, @PathVariable int customerId) {
//		if(isTimeout(sessionsMap.get(token)) != true) {
//			sessionsMap.remove(token);
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Time Out Unauthorized login");
//		}else {
		Session session = sessionsMap.get(token);
		if (session != null) {
			// session.setLastAccessed(??);
			AdminFacade admnFacade = (AdminFacade) session.getFacade();
			try {
				return ResponseEntity.ok(admnFacade.getOneCustomer(customerId));
			} catch (customerWasNotFoundException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized login");
		}
	}
	
}