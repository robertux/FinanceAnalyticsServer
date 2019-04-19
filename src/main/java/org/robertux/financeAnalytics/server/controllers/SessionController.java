package org.robertux.financeAnalytics.server.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.LoginCredentials;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.entities.User;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.robertux.financeAnalytics.server.data.repositories.UsersRepository;
import org.robertux.financeAnalytics.server.security.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class SessionController {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@Autowired
	private CryptoService cryptoService;
	
	@PostMapping(path="/session/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@Valid @RequestBody LoginCredentials login) {
		Optional<User> user = usersRepo.findByName(login.getName());
		
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (!cryptoService.isValid(login.getPassword(), user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		Session session = new Session(user.get().getId());
		sessionsRepo.save(session);
		
		user.get().setPassword("");
		HttpHeaders headers= new HttpHeaders();
		headers.add("Authorization", "Bearer " + session.getId());
		
		return new ResponseEntity<User>(user.get(), headers, HttpStatus.OK);
	}
	
	@PostMapping(path="/session/{userId}/logout", produces=MediaType.ALL_VALUE)
	public ResponseEntity<?> logout(@PathVariable("userId") long userId) {
		
		List<Session> sessions = sessionsRepo.findByUserId(userId);
		
		if (sessions.isEmpty()) {
			return ResponseEntity.badRequest().body("No active session for user");
		} else {
			sessions.forEach(s -> sessionsRepo.delete(s));
		}
		return ResponseEntity.ok().build();
	}
}
