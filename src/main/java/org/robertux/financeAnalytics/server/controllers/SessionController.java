package org.robertux.financeAnalytics.server.controllers;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.LoginCredentials;
import org.robertux.financeAnalytics.server.data.SessionStatus;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.entities.User;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.robertux.financeAnalytics.server.data.repositories.UsersRepository;
import org.robertux.financeAnalytics.server.security.CryptoService;
import org.robertux.financeAnalytics.server.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping(path="/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@Valid @RequestBody LoginCredentials login, HttpServletRequest req) {
		Optional<User> user = usersRepo.findByName(login.getName());
		
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (!cryptoService.isValid(login.getPassword(), user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Session session = new Session(UUID.randomUUID().toString(), user.get().getId());
		session.setUserIp(req.getRemoteAddr());
		sessionsRepo.save(session);
		String token = jwtService.generateToken(session.getId());
		
		user.get().setPassword("");
		HttpHeaders headers= new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, JWTService.AUTH_PREFIX + token);
		
		return new ResponseEntity<User>(user.get(), headers, HttpStatus.OK);
	}
	
	@PostMapping(path="/logout", produces=MediaType.ALL_VALUE)
	public ResponseEntity<?> logout(HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (!session.isPresent()) {
			return ResponseEntity.badRequest().body("No active session found");
		}
		
		Session s = session.get();
		s.setStatus(SessionStatus.INACTIVE.getCode());
		s.setUpdatedAt(new Date());
		sessionsRepo.save(s);
		
		
		return ResponseEntity.ok().build();
	}
}
