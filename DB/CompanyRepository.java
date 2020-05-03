package com.Project3.DB;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Project3.Beans.Company;

// the type interface that make the second layer for the project and gives him a lot of options automatically.
// if you want you can also make more methods that can help you as you please
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	Company findCompanyByEmailAndPassword(String email, String password);
	
}