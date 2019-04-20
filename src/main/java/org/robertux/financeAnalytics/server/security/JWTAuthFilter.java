package org.robertux.financeAnalytics.server.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;

@Component
public class JWTAuthFilter extends GenericFilterBean {
	public static final String LOGIN_URL = "/api/session/login";

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//Si la URL del endpoint corresponde al login, dejar pasar
		if (req.getRequestURI().contains(LOGIN_URL)) {
			chain.doFilter(request, response);
			return;
		}
		
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		
		//Si el request contiene el header Authorization y su valor contiene el prefix Bearer...
		if (authorization != null && authorization.startsWith(JWTService.AUTH_PREFIX)) {
			String token = authorization.replaceAll(JWTService.AUTH_PREFIX, "");
			Claims claims = jwtService.verifyToken(token);
			
			//Si el token es valido y contiene el atributo Subject...
			if (claims != null && claims.getSubject() != null && !claims.getSubject().isEmpty()) {
				Optional<Session> session = sessionsRepo.findById(token);
				
				//Si el token corresponde con el Id de una sesion activa y el subject corresponde con el userId de la sesion entonces dejar pasar
				if (session.isPresent() && String.valueOf(session.get().getUserId()).equals(claims.getSubject())) {
					chain.doFilter(request, response);
					return;
				}
			}
		}
		
		resp.setStatus(HttpStatus.UNAUTHORIZED.value());
		resp.getWriter().write("Missing or invalid JSON Web Token");
	}

}
