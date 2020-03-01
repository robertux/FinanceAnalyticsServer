package org.robertux.financeAnalytics.server.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {
	public static final String AUTH_PREFIX = "Bearer ";
	
	private Logger logger = LogManager.getLogger(this.getClass());
	
	@Value("${org.robertux.jwt.key}")
	private String keyValue;
	
	@Value("${org.robertux.jwt.issuer}")
	private String issuer;
	
	@Value("${org.robertux.jwt.expiry}")
	private Integer expiry;
	
	public String generateToken(long userId) throws JwtException, IllegalArgumentException {
		Date expires = Date.from(LocalDateTime.now(ZoneOffset.UTC).plusSeconds(expiry).toInstant(ZoneOffset.UTC));
		SecretKey key = Keys.hmacShaKeyFor(keyValue.getBytes());
		
		return Jwts.builder().setSubject(String.valueOf(userId)).setExpiration(expires)
				.setIssuer(issuer).signWith(key).compact();
	}
	
	public Claims verifyToken(String tokenValue) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(keyValue.getBytes());

			Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenValue);
			return claims.getBody();
		} catch (JwtException | IllegalArgumentException e) {
			logger.error("Exception in verifyToken: " + e.getMessage(), e);
			return null;
		}
	}
	
	public String getSessionId(String authorization) {
		//Si el request no contiene el header Authorization o su valor no contiene el prefix Bearer...
		if (authorization == null || !authorization.startsWith(JWTService.AUTH_PREFIX)) {
			this.logger.warn("Authorization header {} is null or doesnt have auth prefix", authorization);
			return null;
		}
		
		String token = authorization.replaceAll(JWTService.AUTH_PREFIX, "");
		Claims claims = this.verifyToken(token);
		
		//Si el token no es valido o no contiene el atributo Subject...
		if (claims == null || claims.getSubject() == null || claims.getSubject().isEmpty()) {
			this.logger.warn("Auth token {} has empty claims", token);
			return null;
		}
		
		return claims.getSubject();
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	
	public Integer getExpiry() {
		return expiry;
	}
	
	public void setExpiry(Integer expiry) {
		this.expiry = expiry;
	}
}
