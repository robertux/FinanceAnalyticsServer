package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.Arrays;
import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.AccountType;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Account;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.UsersRepository;
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
	
	@Autowired
	private UsersRepository userRepo;

	@GetMapping(path="/users/{userId}/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccounts(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(accRepo.findAllByUserId(userId));
	}
	
	@GetMapping(path="/accounts/types", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccountType>> getAccountTypes() {
		return ResponseEntity.ok(Arrays.asList(AccountType.values));
	}
	
	@PostMapping(path="/users/{userId}/accounts", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAccount(@RequestBody Account acc, @PathVariable("userId") long userId) {
		if (accRepo.findById(acc.getNumber()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe una cuenta con este número");
		} else {
			acc.setUser(userRepo.findById(userId).get());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		}
	}
	
	@PutMapping(path="/users/{userId}/accounts/{accNumber}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editAccount(@RequestBody Account acc, @PathVariable("userId") long userId, @PathVariable("accNumber") long accNumber) {
		if (accRepo.findById(acc.getNumber()).isPresent()) {
			acc.setUser(userRepo.findById(userId).get());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{userId}/accounts/{accNumber}")
	public ResponseEntity<?> deleteAccount(@PathVariable("accNumber") long accNumber) {
		if (accRepo.findById(accNumber).isPresent()) {
			accRepo.deleteById(accNumber);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
