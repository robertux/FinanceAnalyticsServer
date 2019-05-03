package org.robertux.financeAnalytics.server.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.entities.Account;
import org.robertux.financeAnalytics.server.data.entities.Transaction;
import org.robertux.financeAnalytics.server.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.server.data.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
public class TransactionsController {
	public static final int DEFAULT_PAGE_SIZE = 20;
	public static final PageRequest DEFAULT_PAGER = PageRequest.of(0, DEFAULT_PAGE_SIZE, Direction.DESC, "date");

	@Autowired
	private TransactionsRepository trnRepo;
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping(path="/users/{userId}/accounts/{accNumber}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("userId") long userId, @PathVariable("accNumber") long accountNumber) {
		if (!accRepo.findByAccNumberAndUserId(accountNumber, userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, DEFAULT_PAGER));
	}
	
	@GetMapping(path="/users/{userId}/accounts/{accNumber}/transactions/page/{pageNum}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getTransactionsPaged(@PathVariable("userId") long userId, @PathVariable("accNumber") long accountNumber, @PathVariable("pageNum") int pageNum) {
		if (!accRepo.findByAccNumberAndUserId(accountNumber, userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@GetMapping(path="/users/{userId}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable("userId") long userId) {
		List<Account> accs = accRepo.findAllByUserId(userId);
		List<Long> accNums = accs.stream().map(acc -> acc.getNumber()).collect(Collectors.toList());
		return ResponseEntity.ok(trnRepo.findAllByAccountNums(accNums, DEFAULT_PAGER));
	}
	
	@GetMapping(path="/users/{userId}/transactions/page/{pageNum}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getAllTransactionsPaged(@PathVariable("userId") long userId, @PathVariable("pageNum") int pageNum) {
		List<Account> accs = accRepo.findAllByUserId(userId);
		List<Long> accNums = accs.stream().map(acc -> acc.getNumber()).collect(Collectors.toList());
		return ResponseEntity.ok(trnRepo.findAllByAccountNums(accNums, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@PostMapping(path="/users/{userId}/accounts/{accNumber}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTransaction(@Valid @RequestBody Transaction trn, @PathVariable("userId") long userId, @PathVariable("accNumber") long accountNumber) {
		Optional<Account> acc = accRepo.findByAccNumberAndUserId(accountNumber, userId);
		if (!acc.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		trn.setAccount(acc.get());
		Transaction newTrn = trnRepo.save(trn);
		return ResponseEntity.ok(newTrn);
	}
	
	@PutMapping(path="/users/{userId}/accounts/{accNumber}/transactions/{trnId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editTransaction(@Valid @RequestBody Transaction trn, @PathVariable("userId") long userId, @PathVariable("accNumber") long accountNumber, @PathVariable("trnId") long trnId) {
		Optional<Account> acc = accRepo.findByAccNumberAndUserId(accountNumber, userId);
		if (!acc.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trn.setAccount(acc.get());
			Transaction newTrn = trnRepo.save(trn);
			return ResponseEntity.ok(newTrn);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{userId}/accounts/{accNumber}/transactions/{trnId}")
	public ResponseEntity<?> deleteTransaction(@PathVariable("userId") long userId, @PathVariable("accNumber") long accountNumber, @PathVariable("trnId") String trnId) {
		if (!accRepo.findByAccNumberAndUserId(accountNumber, userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		if (trnRepo.findById(trnId).isPresent()) {
			trnRepo.deleteById(trnId);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
