package com.Project3.DB;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.Project3.Beans.Company;
import com.Project3.Exceptions.cantUpdateCompanyIdException;
import com.Project3.Exceptions.companyWasNotFoundException;

@Repository
@Component
public class CompanyDBDAO {

	//shalom
	@Autowired
	private CompanyRepository companyRepo;
	
	public void addCompany(Company company) {
		companyRepo.save(company);
	}
	
	public void deleteCompany(int companyId) {
		companyRepo.deleteById(companyId);
	}
	
	public void updateCompany(Company company) throws cantUpdateCompanyIdException {
		if(companyRepo.existsById(company.getCompanyId())) {
		companyRepo.save(company);
		}else {
			throw new cantUpdateCompanyIdException();
		}
	}
	
	public Company getCompanyById(int companyId) throws companyWasNotFoundException {
		Optional<Company> opt = companyRepo.findById(companyId);
		if(opt.isPresent()) 
			return opt.get();
		 throw new companyWasNotFoundException();
	}
	
	public List<Company> getAllCompanies(){
		return companyRepo.findAll();
	}
	
	public boolean isCompanyExists(String email, String password) throws companyWasNotFoundException {
		List<Company> allCompanies = (List<Company>) getAllCompanies();
		for (Company c : allCompanies) {
			if (c.getEmail().equals(allCompanies) && c.getPassword().equals(allCompanies))
				 throw new companyWasNotFoundException();
}
		return true;
	}
	public Company findCompanyByEmailAndPassword(String email, String password) {
		return companyRepo.findCompanyByEmailAndPassword(email, password);
	}
}