package com.buyMe.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("")
	public String viewHomePage() {
		System.out.println("Sent Admin Home Page ");
		return "index";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		
		//logival view name
		return "login";
	}
}
