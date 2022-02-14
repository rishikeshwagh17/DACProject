package com.buyMe.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.buyMe.common.entity","com.buyMe.admin.user"})
public class BuyMeBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyMeBackEndApplication.class, args);
	} 

}
