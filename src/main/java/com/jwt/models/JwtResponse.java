package com.jwt.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//This Class is used to build the HTTP Response and send it to the User

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class JwtResponse {

	private String jwtToken;
	
	private String username;

}
