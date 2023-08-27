package com.jwt.security;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.helper.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	/*
	  The Logger class provides methods for logging messages at different levels of severity, such as info, debug, warn, and error.
	   This Logger object can be used to log messages associated with the OncePerRequestFilter class
	   These messages can be useful for tracking the flow of the application, debugging issues, and monitoring its behavior .
	 */
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
   
//	JwtHelper Object to Generate & Validate Tokens
	@Autowired
    private JwtHelper jwtHelper;

//	UserDetailsService Object to provide the information about Authenticated & Authorized Users
    @Autowired
    private UserDetailsService CustomUserDetailsService;
    
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Extracting the header (Bearer token) form the HTTP Request
        String requestHeader = request.getHeader("Authorization");
        
//        Printing the header to the console for debugging 
        logger.info(" Header :  {}", requestHeader);
        
        
        
        String username = null;
        String token = null;
        
        
//        Checking if the header is not null and the header starts with Bearer
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
        	
//        	Extracting the token from the header
            token = requestHeader.substring(7);
           
            try {
//            	Extracting the user name form the token 
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired !!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


        } else {
//        	If the header is null or header is not starting with Bearer then it will print the error on the console
            logger.info("Invalid Header Value !! ");
        }


        /*
         	If the header is not null and the header starts with Bearer then we will validate further
        */
        
//        Checking if the user name is not null and the SecurityContextHolder
        /*
         	- The security context holds information about the current authentication and, optionally, the current authorization.
         	- SecurityContextHolder.getContext().getAuthentication() is used to retrieve the current authentication from the security 
         	 context. The result is then compared to null to check if there is no existing authentication for the current thread.
         */
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            //fetching user details object from user name
            UserDetails userDetails = this.CustomUserDetailsService.loadUserByUsername(username);
           
//            Validation token if it is expired or any other issue
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
           
//            If the token is valid then we will move further
            if (validateToken) {

                /*
                  - The UsernamePasswordAuthenticationToken constructor takes three parameters: userDetails, password, and authorities
                  - Creating a new authentication token for a user that has been authenticated using a user name and password
                  - The token contains information about the authenticated user, such as their user name and authorities, but does not store their password.
                 */
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                /*
                  - this line of code sets additional details about the authentication request, such as the remote IP address and session ID,
                     on the Authentication object.
                  - This information can be useful for auditing and logging purposes.
                 */
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                /*
                  - This line of code sets the current authentication in the security context for the current thread of execution.
                  - The security context holds information about the current authentication and, optionally, the current authorization.
                 */
                SecurityContextHolder.getContext().setAuthentication(authentication);


            } else {
//            	If the token is not valid then logger will print this error on console
                logger.info("Validation fails !!");
            }


        }

        /*
         - Calling filterChain.doFilter(request, response) at the end of a filter’s doFilterInternal method is necessary to ensure
            that the request is properly processed by all filters in the chain before reaching its target resource.
         */
        filterChain.doFilter(request, response);


    }

}
