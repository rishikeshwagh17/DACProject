package com.buyMe.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.buyMe.common.entity.Country;
import com.buyMe.common.entity.Customer;

@Controller
public class CustomerController {

	@Autowired private CustomerService service;
	
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<Country> listCountries = service.listAllCountries();
		model.addAttribute("listCountries", listCountries);
		model.addAttribute("pageTitle", "Customer Registration");
		model.addAttribute("customer", new Customer());
		return "register/register_form";
	}
	//url from registeration form.html
	@PostMapping("/create_customer")
	public String  createCustomer(Customer customer,Model model) {
		service.registerCustomer(customer);
		model.addAttribute("pageTitle", "Registration Succeeded!");
		
		return "/register/register_success";
	}
}
