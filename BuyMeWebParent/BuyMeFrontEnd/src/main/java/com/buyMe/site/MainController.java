package com.buyMe.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
@GetMapping("")
public String viewHomePage() {
	System.out.println("Sent Home Page ");
	return "index";
}
}
