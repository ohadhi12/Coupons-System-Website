package com.Project3.LoginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import com.Project3.Exceptions.invalidClientTypeException;
import com.Project3.Exceptions.loginFaildException;
import com.Project3.Facade.AdminFacade;
import com.Project3.Facade.ClientFacade;
import com.Project3.Facade.CompanyFacade;
import com.Project3.Facade.CustomerFacade;


@Component
public class LoginManager {	
	
	@Autowired
	private ConfigurableApplicationContext ctx;
	
	public ClientFacade login(String email, String password, ClientType type) throws loginFaildException, invalidClientTypeException{
		switch(type) {
		case Admin:
		//check login
		AdminFacade adminFacade = ctx.getBean(AdminFacade.class);
		if(adminFacade.login(email, password)) {
			return adminFacade;
		}
		else {
			throw new loginFaildException();
		}
		
		case Customer:
			//check login
			CustomerFacade customerFacade = ctx.getBean(CustomerFacade.class);
			if(customerFacade.login(email, password)) {
				return customerFacade;
			}
			else {
				throw new loginFaildException();
			}
			
		case Company:
			//check login
			CompanyFacade companyFacade = ctx.getBean(CompanyFacade.class);
			if(companyFacade.login(email, password)) {
				return companyFacade;
			}
			else {
				throw new loginFaildException();
			}			
			default:
				throw new invalidClientTypeException();
		}
	}
}