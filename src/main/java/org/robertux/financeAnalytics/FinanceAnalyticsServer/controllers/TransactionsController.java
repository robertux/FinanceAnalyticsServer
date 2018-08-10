package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Transaction;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.TransactionsRepository;
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
@RequestMapping("/users/{userId}/accounts/{accNumber}/transactions")
public class TransactionsController {

	@Autowired
	private TransactionsRepository trnRepo;
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accNumber") long accountNumber) {
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber));
	}
	
	@PutMapping
	public ResponseEntity<?> addTransaction(@RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber) {
		trn.setAccount(accRepo.findById(accountNumber).get());
		Transaction newTrn = trnRepo.save(trn);
		return ResponseEntity.ok(newTrn);
	}
	
	@PostMapping
	public ResponseEntity<?> editTransaction(@RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trn.setAccount(accRepo.findById(accountNumber).get());
			Transaction newTrn = trnRepo.save(trn);
			return ResponseEntity.ok(newTrn);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteTransaction(@RequestBody Transaction trn) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trnRepo.delete(trn);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
