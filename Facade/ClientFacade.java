package com.Project3.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.Project3.DB.CompanyDBDAO;
import com.Project3.DB.CouponDBDAO;
import com.Project3.DB.CustomerDBDAO;
@Service
@Component
public abstract class ClientFacade {
	@Autowired
	protected CompanyDBDAO companyDB;
	
	@Autowired
	protected CouponDBDAO couponDB;
	
	@Autowired
	protected CustomerDBDAO customerDB;
	
	public abstract boolean login(String email, String password);
}