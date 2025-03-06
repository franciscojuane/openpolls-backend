package com.francisco.openpolls.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.model.User;
import com.francisco.openpolls.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	public User create(@Valid User user, String password) {

		String encodedPassword = passwordEncoder.encode(password);
		user.setPassword(encodedPassword);
		user = userRepository.save(user);
		
		return user;
	}


	public User update(User user, String password) {
		User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));		
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            if (password != null) {
            	String encodedPassword = passwordEncoder.encode(password);
            	existingUser.setPassword(encodedPassword);
            }
            existingUser.setEmail(user.getEmail());
            existingUser.setEffectiveDate(user.getEffectiveDate());
            existingUser.setExpirationDate(user.getExpirationDate());
	        existingUser = userRepository.save(existingUser);	        
	        return existingUser;
	}
	
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	public Page<User> findAll(Pageable pageable){
		Page<User> page = userRepository.findAll(pageable);
		return page;
	}
	
	public Optional<User> findById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser;
	}

	public Optional<User> findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		return optionalUser;
	}

}
