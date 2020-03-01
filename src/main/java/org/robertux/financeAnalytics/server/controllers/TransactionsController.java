package org.robertux.financeAnalytics.server.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.entities.Account;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.entities.Transaction;
import org.robertux.financeAnalytics.server.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.robertux.financeAnalytics.server.data.repositories.TransactionsRepository;
import org.robertux.financeAnalytics.server.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
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
	private JWTService jwtService;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@Autowired
	private TransactionsRepository trnRepo;
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping(path="/accounts/{accNumber}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accNumber") long accountNumber, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (!accRepo.findByAccNumberAndUserId(accountNumber, session.get().getUserId()).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, DEFAULT_PAGER));
	}
	
	@GetMapping(path="/accounts/{accNumber}/transactions/page/{pageNum}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getTransactionsPaged(@PathVariable("accNumber") long accountNumber, @PathVariable("pageNum") int pageNum, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (!accRepo.findByAccNumberAndUserId(accountNumber, session.get().getUserId()).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trnRepo.findAllByAccountNum(accountNumber, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@GetMapping(path="/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		List<Account> accs = accRepo.findAllByUserId(session.get().getUserId());
		List<Long> accNums = accs.stream().map(acc -> acc.getNumber()).collect(Collectors.toList());
		return ResponseEntity.ok(trnRepo.findAllByAccountNums(accNums, DEFAULT_PAGER));
	}
	
	@GetMapping(path="/transactions/page/{pageNum}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Transaction>> getAllTransactionsPaged(@PathVariable("pageNum") int pageNum, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		List<Account> accs = accRepo.findAllByUserId(session.get().getUserId());
		List<Long> accNums = accs.stream().map(acc -> acc.getNumber()).collect(Collectors.toList());
		return ResponseEntity.ok(trnRepo.findAllByAccountNums(accNums, PageRequest.of(pageNum, DEFAULT_PAGE_SIZE, Direction.DESC, "date")));
	}
	
	@PostMapping(path="/accounts/{accNumber}/transactions", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addTransaction(@Valid @RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		Optional<Account> acc = accRepo.findByAccNumberAndUserId(accountNumber, session.get().getUserId());
		if (!acc.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		trn.setAccount(acc.get());
		Transaction newTrn = trnRepo.save(trn);
		return ResponseEntity.ok(newTrn);
	}
	
	@PutMapping(path="/accounts/{accNumber}/transactions/{trnId}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editTransaction(@Valid @RequestBody Transaction trn, @PathVariable("accNumber") long accountNumber, @PathVariable("trnId") long trnId, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		Optional<Account> acc = accRepo.findByAccNumberAndUserId(accountNumber, session.get().getUserId());
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
	
	@DeleteMapping("/accounts/{accNumber}/transactions/{trnId}")
	public ResponseEntity<?> deleteTransaction(@PathVariable("accNumber") long accountNumber, @PathVariable("trnId") String trnId, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (!accRepo.findByAccNumberAndUserId(accountNumber, session.get().getUserId()).isPresent()) {
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
