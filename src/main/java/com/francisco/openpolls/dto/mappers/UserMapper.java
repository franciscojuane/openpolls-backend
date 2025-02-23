package com.francisco.openpolls.dto.mappers;

import org.springframework.stereotype.Component;

import com.francisco.openpolls.dto.UserResponse;
import com.francisco.openpolls.model.User;

@Component
public class UserMapper {

	public UserResponse userToUserResponse(User user) {
		return UserResponse.builder()
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
