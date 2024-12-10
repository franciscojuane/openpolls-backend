package com.francisco.openpolls.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.User;
import com.francisco.openpolls.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	public User createUser(String firstName, String lastName, String password, String email) {
		String encodedPassword = passwordEncoder.encode(password);

		User user = User.builder().firstName("Francisco").lastName("Juane").password(encodedPassword).email(email)
				.build();
		user = userRepository.save(user);
		
		return user;
	}

}
