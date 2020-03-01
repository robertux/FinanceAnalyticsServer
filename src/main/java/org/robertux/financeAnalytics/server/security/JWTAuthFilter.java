package org.robertux.financeAnalytics.server.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.robertux.financeAnalytics.server.data.SessionStatus;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

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
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		//Si el token corresponde con el Id de una sesion activa entonces dejar pasar
		if (session.isPresent() && SessionStatus.ACTIVE.getCode().equals(session.get().getStatus())) {
			chain.doFilter(request, response);
			return;
		}
		
		
		resp.setStatus(HttpStatus.UNAUTHORIZED.value());
		resp.getWriter().write("Missing or invalid JSON Web Token");
	}

}
