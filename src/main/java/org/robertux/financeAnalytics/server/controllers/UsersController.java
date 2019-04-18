package org.robertux.financeAnalytics.server.controllers;

import java.util.Optional;

import org.robertux.financeAnalytics.server.data.LoginCredentials;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.entities.User;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.robertux.financeAnalytics.server.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@PostMapping(path="/users/login", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody LoginCredentials login) {
		Optional<User> user = usersRepo.findByName(login.getName());
		
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (!usersRepo.isValid(login.getPassword(), user.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		user.get().setPassword("");
		Session session = new Session(user.get().getId());
		sessionsRepo.save(session);
		
		ResponseEntity<User> entity = ResponseEntity.ok(user.get());
		entity.getHeaders().add("Authorization", "Bearer " + session.getId());
		
		return entity;
	}
	
	@PostMapping(path="/users/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@RequestBody User user) {
		if (usersRepo.findByName(user.getName()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con este nombre");
		}
		
		user.setPassword(usersRepo.encrypt(user.getPassword()));
		User newUser = usersRepo.save(user);
		newUser.setPassword("");
		
		return ResponseEntity.ok(newUser);
	}
	
	@PostMapping(path="/users/{userId}/logout", produces=MediaType.ALL_VALUE)
	public ResponseEntity<?> logout(@PathVariable("userId") long userId) {
		
		sessionsRepo.findByUserId(userId).forEach(s -> sessionsRepo.delete(s));
		return ResponseEntity.ok().build();
	}
}
