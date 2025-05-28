package com.francisco.openpolls.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		SecretKey secretKey = getSecretKey();
		
		return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration*1000))
				
				.signWith(secretKey).compact();
	}

	

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	
	public boolean isTokenValid(String token) {
		SecretKey secretKey = getSecretKey();
		try {
			Jwt<JwsHeader, Claims> jwt = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			Date expirationDate = jwt.getPayload().getExpiration();
			return expirationDate.after(new Date()); 
			
		}catch(Exception e) {
			return false;
		}
	}



	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
		return secretKey;
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, claim -> claim.getExpiration());
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	 private Claims extractAllClaims(String token) {
	        return Jwts
	                .parser()
	                .verifyWith(getSecretKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	 }
}
