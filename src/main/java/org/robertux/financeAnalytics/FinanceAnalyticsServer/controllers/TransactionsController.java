package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Transaction;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/users/{userId}/accounts/{accNumber}/transactions")
public class TransactionsController {

	@Autowired
	private TransactionsRepository trnRepo;

	@GetMapping()
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accNumber") long accountNumber) {
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber));
	}
	
	@PutMapping()
	public ResponseEntity<?> addTransaction(@RequestBody Transaction trn) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe una transacción con este número");
		} else {
			Transaction newTrn = trnRepo.save(trn);
			return ResponseEntity.ok(newTrn);
		}
	}
	
	@PostMapping()
	public ResponseEntity<?> editTransaction(@RequestBody Transaction trn) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			Transaction newTrn = trnRepo.save(trn);
			return ResponseEntity.ok(newTrn);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteTransaction(@RequestBody Transaction trn) {
		if (trnRepo.findById(trn.getId()).isPresent()) {
			trnRepo.delete(trn);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
