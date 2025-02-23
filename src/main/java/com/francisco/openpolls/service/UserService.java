package com.francisco.openpolls.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francisco.openpolls.dto.UserCreateRequest;
import com.francisco.openpolls.dto.UserUpdateRequest;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.repository.UserRepository;

import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	
	public User create(@Valid UserCreateRequest userCreateRequest) {

		String firstName = userCreateRequest.getFirstName();
		String lastName = userCreateRequest.getLastName();
		String password = userCreateRequest.getPassword();
		String email = userCreateRequest.getEmail();
		LocalDateTime effectiveDate = userCreateRequest.getEffectiveDate();
		LocalDateTime expirationDate = userCreateRequest.getExpirationDate();


		String encodedPassword = passwordEncoder.encode(password);

		User user = User.builder().firstName(firstName).lastName(lastName).password(encodedPassword).email(email)
				.build();
		
		user.setEffectiveDate(effectiveDate);
		user.setExpirationDate(expirationDate);
		user = userRepository.save(user);
		
		return user;
	}


	public User update(UserUpdateRequest userUpdateRequest, Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		if (userUpdateRequest.isUpdateFirstName()) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }

        if (userUpdateRequest.isUpdateLastName()) {
            user.setLastName(userUpdateRequest.getLastName());
        }

        if (userUpdateRequest.isUpdatePassword()) {
            user.setPassword(userUpdateRequest.getPassword());
        }

        if (userUpdateRequest.isUpdateEmail()) {
            user.setEmail(userUpdateRequest.getEmail());
        }

        if (userUpdateRequest.isUpdateEffectiveDate()) {
            user.setEffectiveDate(userUpdateRequest.getEffectiveDate());
        }

        if (userUpdateRequest.isUpdateExpirationDate()) {
            user.setExpirationDate(userUpdateRequest.getExpirationDate());
        }
        user = userRepository.save(user);
        return user;
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
