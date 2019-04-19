package org.robertux.financeAnalytics.server.security;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTService {

	public static final String ISSUER = "org.robertux";
	public static final String KEY = "-__r0b3rtUx__-";
	public static final Charset UTF8 = Charset.defaultCharset();
	
	@Value("${org.robertux.jwt.key}")
	private String keyValue;
	
	public String generateToken(String userName) throws JwtException, IllegalArgumentException {
		Date expires = Date.from(LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.UTC));
		SecretKey key = Keys.hmacShaKeyFor(keyValue.getBytes());
		
		return Jwts.builder().setSubject(userName).setExpiration(expires)
				.setIssuer(ISSUER).signWith(key).compact();
	}
	
	public String verifyToken(String tokenValue) throws JwtException, IllegalArgumentException {
		SecretKey key = Keys.hmacShaKeyFor(keyValue.getBytes());
		Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenValue);
		return claims.getBody().getSubject();
	}
}
