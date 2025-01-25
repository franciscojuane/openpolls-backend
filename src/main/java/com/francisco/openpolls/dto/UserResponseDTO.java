package com.francisco.openpolls.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.francisco.openpolls.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	public LocalDateTime effectiveDate;
	
	public LocalDateTime expirationDate;
	
	public LocalDateTime creationTime;
	
	public LocalDateTime updateTime;



public static UserResponseDTO fromUser(User user) {
	return UserResponseDTO.builder()
            .id(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .effectiveDate(user.getEffectiveDate())
            .expirationDate(user.getExpirationDate())
            .creationTime(user.getCreationTime())
            .updateTime(user.getUpdateTime())
            .build();
}

}