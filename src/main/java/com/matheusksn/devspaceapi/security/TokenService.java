package com.matheusksn.devspaceapi.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheusksn.devspaceapi.entities.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(User user) {
				
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("dev-space-auth")
					.withSubject(user.getLogin())
					.withExpiresAt(generateExpirationTokenTime())
					.sign(algorithm);
			return token;
		}
		catch(JWTCreationException exception){
			throw new RuntimeException("error while generating token");	
		}
		

	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("dev-space-auth")
					.build()
					.verify(token)
					.getSubject();
		}
		catch(JWTVerificationException exception) {
			return "";
		}

	}
	
	private Instant generateExpirationTokenTime() {
		return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.of("-03:00"));
	}
}
