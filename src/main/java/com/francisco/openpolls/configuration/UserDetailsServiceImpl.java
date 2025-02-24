package com.francisco.openpolls.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.model.Permission;
import com.francisco.openpolls.model.Role;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
		
		Set<GrantedAuthority> authorities = new HashSet<>();
	    for (Role role : user.getRoles()) {
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
	        for (Permission permission : role.getPermissions()) {
	            authorities.add(new SimpleGrantedAuthority(permission.getName()));
	        }
	    }

	    return new org.springframework.security.core.userdetails.User(
	        user.getEmail(),
	        user.getPassword(),
	        authorities
	    );
	

	}

}
