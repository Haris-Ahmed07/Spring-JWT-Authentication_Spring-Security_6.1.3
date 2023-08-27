package com.jwt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


// Configuration for Users And User_Authentication
@Configuration
public class AppConfig {

	
//	Password encoder bean
    @Bean
    BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
//    InMemoryUserDetailService Bean to bypass DataBase Configuration
	@Bean
    UserDetailsService userDetailsService() {
        UserDetails user1 = User.builder().username("Haris").password(passwordEncoder().encode("123")).roles("ADMIN").build();
        UserDetails user2 = User.builder().username("Ahmed").password(passwordEncoder().encode("123")).roles("USER").build();
        
        return new InMemoryUserDetailsManager(user1,user2);
    }
	
//	Authentication Manager Bean to authenticate the users
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
