package org.robertux.financeAnalytics.server.controllers;

import java.util.Optional;

import org.robertux.financeAnalytics.server.data.entities.User;
import org.robertux.financeAnalytics.server.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	@Autowired
	private UsersRepository usersRepo;
	
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
	
	@PutMapping(path="/users/{userId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editUser(@RequestBody User user, @PathVariable("userId") long userId) {
		if (!usersRepo.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<User> sameName = usersRepo.findByName(user.getName());
		if (sameName.isPresent() && sameName.get().getId() != userId) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con este nombre");
		}
		
		user.setPassword(usersRepo.encrypt(user.getPassword()));
		User updatedUser = usersRepo.save(user);
		updatedUser.setPassword("");
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping(path="/users/{userId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delUser(@PathVariable("userId") long userId) {
		if (!usersRepo.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		usersRepo.deleteById(userId);
		return ResponseEntity.ok().build();
	}
}
