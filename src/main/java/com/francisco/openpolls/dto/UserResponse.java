package com.francisco.openpolls.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.francisco.openpolls.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	public LocalDateTime effectiveDate;
	
	public LocalDateTime expirationDate;
	
	public LocalDateTime creationTime;
	
	public LocalDateTime updateTime;
	
	public Set<Role> roles;
	
	public Collection<? extends GrantedAuthority> authorities;





}