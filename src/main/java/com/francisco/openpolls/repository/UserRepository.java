package com.francisco.openpolls.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);

}
