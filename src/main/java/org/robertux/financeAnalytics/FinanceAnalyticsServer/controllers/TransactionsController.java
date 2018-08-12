package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Transaction;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

	@GetMapping("/users/{userId}/accounts/{accNumber}/transactions")
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accNumber") long accountNumber) {
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, DEFAULT_PAGER));
	}
	
	@GetMapping("/users/{userId}/accounts/{accNumber}/transactions/page/{pageNum}")
	public ResponseEntity<List<Transaction>> getTransactionsPaged(@PathVariable("accNumber") long accountNumber, @PathVariable("pageNum") int pageNum) {
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@GetMapping("/users/{userId}/transactions")
	public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable("userId") long userId) {
		return ResponseEntity.ok(trnRepo.findAllByUser(userId, DEFAULT_PAGER));
	}
	
	@GetMapping("/users/{userId}/transactions/page/{pageNum}")
	public ResponseEntity<List<Transaction>> getAllTransactionsPaged(@PathVariable("userId") long userId, @PathVariable("pageNum") int pageNum) {
		return ResponseEntity.ok(trnRepo.findAllByUser(userId, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@PutMapping("/users/{userId}/accounts/{accNumber}/transactions")
	public ResponseEntity<?> addTransaction(@RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber) {
		trn.setAccount(accRepo.findById(accountNumber).get());
		Transaction newTrn = trnRepo.save(trn);
		return ResponseEntity.ok(newTrn);
	}
	
	@PostMapping("/users/{userId}/accounts/{accNumber}/transactions")
	public ResponseEntity<?> editTransaction(@RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trn.setAccount(accRepo.findById(accountNumber).get());
			Transaction newTrn = trnRepo.save(trn);
			return ResponseEntity.ok(newTrn);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/users/{userId}/accounts/{accNumber}/transactions")
	public ResponseEntity<?> deleteTransaction(@RequestBody Transaction trn) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trnRepo.delete(trn);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
