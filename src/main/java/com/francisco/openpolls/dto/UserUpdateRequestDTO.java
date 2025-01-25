package com.francisco.openpolls.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {

	private String firstName;
	
	private boolean updateFirstName;
	
	private String lastName;
	
	private boolean updateLastName;
	
	private String password;
	
	private boolean updatePassword;

	private String email;
	
	private boolean updateEmail;

	public LocalDateTime effectiveDate;
	
	private boolean updateEffectiveDate;

	public LocalDateTime expirationDate;
	
	private boolean updateExpirationDate;


}