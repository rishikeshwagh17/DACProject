package com.buyMe.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
	
	@Test
	public void testEncodePassword() {
		//create bcrypt instance
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//give some passcode
		String rawPassword = "Swap1234";
		//encode it using encode method
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(encodedPassword);
		//check two password matches or not with matches method
		boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
		assertThat(matches).isTrue();
	}
	

}
