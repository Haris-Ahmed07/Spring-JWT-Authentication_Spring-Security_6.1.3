package com.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.security.JwtAuthenticationEntryPoint;
import com.jwt.security.JwtAuthenticationFilter;

//Configuration for the Authorization
@Configuration
public class SecurityConfig {

	
	@Autowired
	private JwtAuthenticationEntryPoint point;
	
//	Authentication Filter Bean to check all the requirements
	@Autowired
	private JwtAuthenticationFilter filter;
	
	
//	SecurityFilterChain Bean to configure all the authorization
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
        	.cors(cors -> cors.disable())
        	.authorizeHttpRequests(auth -> auth
        								.requestMatchers("/home/getAll").authenticated()
        								.requestMatchers("/home/admin").hasRole("ADMIN")
        								.requestMatchers("/home/user").hasRole("USER")
        								.requestMatchers("/auth/login").permitAll()
        				                .anyRequest()
        				                .authenticated())
        	.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
        	.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        		 
        
  
        return http.build();
    }
	
}
