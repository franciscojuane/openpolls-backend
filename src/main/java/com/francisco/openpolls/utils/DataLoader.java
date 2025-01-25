package com.francisco.openpolls.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.dto.UserCreateRequestDTO;
import com.francisco.openpolls.model.User;
import com.francisco.openpolls.service.UserService;

@Component
public class DataLoader implements InitializingBean {

	@Autowired
	UserService userService;

	@Override
	public void afterPropertiesSet() throws Exception {

		UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO.builder().firstName("Francisco")
				.lastName("Juane").email("admin@admin.com").password("admin").build();
		User user1 = userService.createUser(userCreateRequestDTO);

	}

}
