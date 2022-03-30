package com.buyMe.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
//spring config class
@Configuration
//enable security
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	//method for getting userdeatils service
	@Bean
	public UserDetailsService userDetails() {
		return new BuymeUserDetailsService();
	}
	
	//method for authentication provider tell security look user in the database and authenticate it
	public DaoAuthenticationProvider authenticate() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetails());
		authProvider.setPasswordEncoder(PasswordEncoder());
		return authProvider;
	}
	
	//override method to configure the authentication provider
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticate());
	}
	
	//password encoder function
	//look mams function
	@Bean
	public PasswordEncoder PasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/users/**").hasAuthority("Admin")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.usernameParameter("email").permitAll() 
		.and().logout().permitAll().and().rememberMe().key("rem-me-key").rememberMeParameter("remember");
		
		
	}

	//also we have to override for showing static resources
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		//webjars because we keep bootstrap and css under webjars
		web.ignoring().antMatchers("/images/**","/css/**","/script/**","/webjars/**");
	
	}
	
	
	
	
	//old method
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		//permit request
//		//prev we allowed any request
//		http.authorizeRequests().anyRequest().permitAll();
//	}
	
	

}
