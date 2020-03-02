package org.robertux.financeAnalytics.server.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.entities.User;
import org.robertux.financeAnalytics.server.data.repositories.UsersRepository;
import org.robertux.financeAnalytics.server.security.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersRepository usersRepo;
	
	@Autowired
	private CryptoService cryptoService;
	
	@GetMapping(path="/{userId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUser(@PathVariable("userId") long userId) {
		Optional<User> user = usersRepo.findById(userId);
		
		if (user.isPresent()) {
			user.get().setPassword("");
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(path="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
		if (usersRepo.findByName(user.getName()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con este nombre");
		}
		
		user.setPassword(cryptoService.encrypt(user.getPassword()));
		User newUser = usersRepo.save(user);
		newUser.setPassword("");
		
		return ResponseEntity.ok(newUser);
	}
	
	@PutMapping(path="/{userId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editUser(@Valid @RequestBody User user, @PathVariable("userId") long userId) {
		if (!usersRepo.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Optional<User> sameName = usersRepo.findByName(user.getName());
		if (sameName.isPresent() && sameName.get().getId() != userId) {
			return ResponseEntity.badRequest().body("Ya existe un usuario con este nombre");
		}
		
		User editedUser = usersRepo.findById(userId).get();
		editedUser.setName(user.getName());
		editedUser.setStatus(user.getStatus());
		
		if (!user.getPassword().isEmpty()) {
			editedUser.setPassword(cryptoService.encrypt(user.getPassword()));
		}
		
		User updatedUser = usersRepo.save(editedUser);
		updatedUser.setPassword("");
		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping(path="/{userId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delUser(@PathVariable("userId") long userId) {
		if (!usersRepo.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		usersRepo.deleteById(userId);
		return ResponseEntity.ok().build();
	}
}
