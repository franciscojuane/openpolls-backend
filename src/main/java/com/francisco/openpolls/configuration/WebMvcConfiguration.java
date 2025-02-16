package com.francisco.openpolls.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.francisco.openpolls.utils.resolver.SelectedUserArgumentResolver;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer{

	@Autowired
	SelectedUserArgumentResolver selectedUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(selectedUserArgumentResolver);
	}

	
	
}
