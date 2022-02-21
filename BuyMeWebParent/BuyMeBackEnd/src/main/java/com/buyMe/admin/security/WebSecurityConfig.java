package com.buyMe.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//spring config class
@Configuration
//enable security
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	//password encoder function
	//look mams function
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().anyRequest().authenticated()
		.and().formLogin().loginPage("/login").usernameParameter("email").permitAll();
	}

	//also we have to override for showing static resources
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		//webjars because we keep bootstrap and css under webjars
		web.ignoring().antMatchers("/images/**","/css/**","/script/**","/webjars/**");
		
	}
	
	
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		//permit request
//		//prev we allowed any request
//		http.authorizeRequests().anyRequest().permitAll();
//	}
	
	//writing new method

}
