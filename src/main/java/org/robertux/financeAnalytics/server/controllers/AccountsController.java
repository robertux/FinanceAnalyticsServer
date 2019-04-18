package org.robertux.financeAnalytics.server.controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.AccountType;
import org.robertux.financeAnalytics.server.data.entities.Account;
import org.robertux.financeAnalytics.server.data.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping(path="/users/{userId}/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccounts(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(accRepo.findAllByUserId(userId));
	}
	
	@GetMapping(path="/accounts/types", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccountType>> getAccountTypes() {
		return ResponseEntity.ok(Arrays.asList(AccountType.SAVINGS.getAll()));
	}
	
	@PostMapping(path="/users/{userId}/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAccount(@Valid @RequestBody Account acc, @PathVariable("userId") long userId) {
		if (accRepo.findByAccNumberAndUserId(acc.getNumber(), userId).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe una cuenta con este número");
		} else {
			acc.setUserId(userId);
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		}
	}
	
	@PutMapping(path="/users/{userId}/accounts/{accNumber}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editAccount(@Valid @RequestBody Account acc, @PathVariable("userId") long userId, @PathVariable("accNumber") long accNumber) {
		if (accRepo.findByAccNumberAndUserId(acc.getNumber(), userId).isPresent()) {
			acc.setUserId(userId);
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{userId}/accounts/{accNumber}")
	public ResponseEntity<?> deleteAccount(@PathVariable("userId") long userId, @PathVariable("accNumber") long accNumber) {
		if (accRepo.findByAccNumberAndUserId(accNumber, userId).isPresent()) {
			accRepo.deleteById(accNumber);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
