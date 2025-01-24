package com.francisco.openpolls.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.francisco.openpolls.repository.UserRepository;

@Controller
@RequestMapping("/users")
public class UsersController {

	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/test")
	public ResponseEntity<?> getUser(){
		return ResponseEntity.ok(userRepository.findByEmail("admin@admin.com"));
	}
}
