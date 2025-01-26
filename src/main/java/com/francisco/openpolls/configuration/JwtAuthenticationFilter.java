package com.francisco.openpolls.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.francisco.openpolls.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	HandlerExceptionResolver handlerExceptionResolver;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			String jwtToken = authorizationHeader.substring(7);
			
			if (jwtService.isTokenValid(jwtToken)) {
				String userEmail = jwtService.extractClaim(jwtToken, claim -> claim.getSubject());
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
				if (userEmail != null && authentication ==null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

					if (jwtService.isTokenValid(jwtToken)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authToken);
					}
				}				
			}

		}catch(Exception e) {
			handlerExceptionResolver.resolveException(request, response, null, e);
		}
		
		filterChain.doFilter(request, response);	
		
	}

}
