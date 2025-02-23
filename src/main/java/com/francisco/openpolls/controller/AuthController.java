package com.francisco.openpolls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.security.JwtService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LoginRequest {
	private String username;
	private String password;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LoginResponse {
	private String token;
}


@RequestMapping("/auth")
@Controller
public class AuthController {

	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
		
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
		
		if (userDetails == null)
			throw new RuntimeException("User not found");
		
		String password = loginRequest.getPassword();
		
		boolean passwordMatches = passwordEncoder.matches(password, userDetails.getPassword());
		
		if (passwordMatches) {
			String token = jwtService.buildToken(null, userDetails, 3600);
			return ResponseEntity.ok(LoginResponse.builder().token(token).build());
		} else {
			throw new RuntimeException("Incorrect password");
		}
		
		
		
		
	}
}
