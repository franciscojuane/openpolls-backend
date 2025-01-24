package com.francisco.openpolls.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.model.User;
import com.francisco.openpolls.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userDetails = userRepository.findByEmail(username);
		if (userDetails.isPresent()) {
			return userDetails.get();
		}else {
			throw new UsernameNotFoundException("User not found.");
		}

	}

}
