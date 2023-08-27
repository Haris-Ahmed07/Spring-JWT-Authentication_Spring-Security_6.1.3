package com.jwt.helper;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*	JWT Helper Class, it provides many services like
 * 1) generate token
 * 2) validate token
 * 3) extract user name from token
 * 4) extract expiration Date from token
 * 5) extract roles from token 
 * 6) check token expiration validity 
*/
@Component
public class JwtHelper {

//    Expiration Validity From Current Time
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 *1000;
    
//    Secret Encoding Key 
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

//	  Extract user name from token    
    public String getUsernameFromToken(String token) 
    {
        return getClaimFromToken(token, Claims::getSubject);
    }
//	  Extract Expiration Date from token 
    public Date getExpirationDateFromToken(String token) 
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }
//	  Extract Specific Claim from token 
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) 
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
//	  Extract All Claims from token 
    private Claims getAllClaimsFromToken(String token) 
    {
    	return Jwts
    	        .parserBuilder()
    	        .setSigningKey(getSigningKey())
    	        .build()
    	        .parseClaimsJws(token)
    	        .getBody();

    }
//	  Check the Token Expiration Validity 
    private Boolean isTokenExpired(String token) 
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


//	  Generate Token  
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
          .builder()
          .setClaims(extraClaims)
          .setSubject(userDetails.getUsername())
          .setIssuedAt(new Date(System.currentTimeMillis()))
          .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
          .signWith(getSigningKey(), SignatureAlgorithm.HS256)
          .compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
//    Return TypeCasted Security Key (String -> java.security.Key )
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}