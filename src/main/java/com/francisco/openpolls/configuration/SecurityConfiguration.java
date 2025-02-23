package com.francisco.openpolls.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
	
    //private AuthenticationProvider authenticationProvider;
	
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
    	http.csrf(csrf -> csrf.disable()) 
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/auth/login").permitAll() 
             .requestMatchers("/h2-console/**").permitAll()
             .requestMatchers("/public/**").permitAll()
             .anyRequest().authenticated()           
         )
         .sessionManagement(session -> session
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
         )
         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    	 http.csrf(AbstractHttpConfigurer::disable);
    	 http.headers(HeadersConfigurer::disable);
    	 http.cors(corsConfigurationSource());
     return http.build(); 
    }

    @Bean
    Customizer<CorsConfigurer<HttpSecurity>> corsConfigurationSource() {
    	   return cors -> {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(List.of("GET","POST","PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);
        cors.configurationSource(source);
        };
    }
}