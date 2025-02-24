package com.francisco.openpolls.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.francisco.openpolls.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@NotNull
	private String password;

	@NotNull
	private String email;

	public LocalDateTime effectiveDate;

	public LocalDateTime expirationDate;
	
	private Set<Role> roles;


}