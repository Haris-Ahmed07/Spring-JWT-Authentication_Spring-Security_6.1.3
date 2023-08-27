package com.jwt.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//	This Class is used to take the HTTP request and build it into this class

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtRequest {

	String email;
	String password;
	
}
