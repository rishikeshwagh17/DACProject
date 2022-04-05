package com.buyMe.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buyMe.common.entity.Country;
import com.buyMe.common.entity.Customer;
import com.buyMe.setting.CountryRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class CustomerService {
	
	@Autowired private CountryRepository countryRepo;
	@Autowired private CustomerRepository customerRepo;
	@Autowired PasswordEncoder PasswordEncoder;
	
	public List<Country> listAllCountries(){
		return countryRepo.findAllByOrderByNameAsc();
	}
	
	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
	
	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		//when customer registers its status is false
		customer.setEnabled(false);
		customer.setCreatedTime(new Date());
		//set unique random string as a verification code
		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);
		System.out.println("verification code:" + randomCode);
	}

	private void encodePassword(Customer customer) {
		// TODO Auto-generated method stub
		String encodedPassword = PasswordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}
}
