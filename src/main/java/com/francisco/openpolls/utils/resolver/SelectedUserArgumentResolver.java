package com.francisco.openpolls.utils.resolver;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.francisco.openpolls.model.User;
import com.francisco.openpolls.service.UserService;

@Component
public class SelectedUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	UserService userService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasParameter = parameter.hasParameterAnnotation(SelectedUser.class);
		boolean isParameterType = parameter.getParameterType().equals(User.class);
		return hasParameter && isParameterType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = (User) authentication.getPrincipal();
		
		Optional<User> optionalUser = userService.findByEmail(user.getEmail());

		if (optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			throw new RuntimeException("Selected User cannot be found.");
		}
	}

}
