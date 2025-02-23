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

	
	public User create(@Valid UserCreateRequest userCreateRequestDTO) {

		String firstName = userCreateRequestDTO.getFirstName();
		String lastName = userCreateRequestDTO.getLastName();
		String password = userCreateRequestDTO.getPassword();
		String email = userCreateRequestDTO.getEmail();
		LocalDateTime effectiveDate = userCreateRequestDTO.getEffectiveDate();
		LocalDateTime expirationDate = userCreateRequestDTO.getExpirationDate();


		String encodedPassword = passwordEncoder.encode(password);

		User user = User.builder().firstName(firstName).lastName(lastName).password(encodedPassword).email(email)
				.build();
		
		user.setEffectiveDate(effectiveDate);
		user.setExpirationDate(expirationDate);
		user = userRepository.save(user);
		
		return user;
	}


	public User update(UserUpdateRequest userUpdateRequestDTO, Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		if (userUpdateRequestDTO.isUpdateFirstName()) {
            user.setFirstName(userUpdateRequestDTO.getFirstName());
        }

        if (userUpdateRequestDTO.isUpdateLastName()) {
            user.setLastName(userUpdateRequestDTO.getLastName());
        }

        if (userUpdateRequestDTO.isUpdatePassword()) {
            user.setPassword(userUpdateRequestDTO.getPassword());
        }

        if (userUpdateRequestDTO.isUpdateEmail()) {
            user.setEmail(userUpdateRequestDTO.getEmail());
        }

        if (userUpdateRequestDTO.isUpdateEffectiveDate()) {
            user.setEffectiveDate(userUpdateRequestDTO.getEffectiveDate());
        }

        if (userUpdateRequestDTO.isUpdateExpirationDate()) {
            user.setExpirationDate(userUpdateRequestDTO.getExpirationDate());
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
