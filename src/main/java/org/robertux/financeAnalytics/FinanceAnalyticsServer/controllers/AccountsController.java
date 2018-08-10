package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.AccountTypes;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Account;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/users/{userId}/accounts")
public class AccountsController {
	
	@Autowired
	private AccountsRepository accRepo;
	
	@Autowired
	private UsersRepository userRepo;

	@GetMapping
	public ResponseEntity<List<Account>> getAccounts(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(accRepo.findAllByUserId(userId));
	}
	
	@GetMapping("/types")
	public ResponseEntity<List<String>> getAccountTypes() {
		return ResponseEntity.ok(AccountTypes.listNames());
	}
	
	@PutMapping
	public ResponseEntity<?> addAccount(@RequestBody Account acc, @PathVariable("userId") long userId) {
		if (accRepo.findById(acc.getNumber()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe una cuenta con este número");
		} else {
			acc.setUser(userRepo.findById(userId).get());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		}
	}
	
	@PostMapping
	public ResponseEntity<?> editAccount(@RequestBody Account acc, @PathVariable("userId") long userId) {
		if (accRepo.findById(acc.getNumber()).isPresent()) {
			acc.setUser(userRepo.findById(userId).get());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteAccount(@RequestBody Account acc) {
		if (accRepo.findById(acc.getNumber()).isPresent()) {
			accRepo.delete(acc);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
