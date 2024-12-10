package com.francisco.openpolls.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.francisco.openpolls.model.User;
import com.francisco.openpolls.model.service.UserService;

@Component
public class DataLoader implements InitializingBean {

	@Autowired
	UserService userService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
	
	User user1 = userService.createUser("Francisco", "Juane", "admin" , "admin@admin.com");
	
	
	}

}
