package org.robertux.financeAnalytics.server.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.robertux.financeAnalytics.server.data.AccountType;
import org.robertux.financeAnalytics.server.data.entities.Account;
import org.robertux.financeAnalytics.server.data.entities.Session;
import org.robertux.financeAnalytics.server.data.repositories.AccountsRepository;
import org.robertux.financeAnalytics.server.data.repositories.SessionsRepository;
import org.robertux.financeAnalytics.server.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/accounts")
public class AccountsController {
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private SessionsRepository sessionsRepo;
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping(path="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Account>> getAccounts(HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		return ResponseEntity.ok(accRepo.findAllByUserId(session.get().getUserId()));
	}
	
	@GetMapping(path="/types", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AccountType>> getAccountTypes() {
		return ResponseEntity.ok(Arrays.asList(AccountType.SAVINGS.getAll()));
	}
	
	@PostMapping(path="/", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addAccount(@Valid @RequestBody Account acc, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (accRepo.findByAccNumberAndUserId(acc.getNumber(), session.get().getUserId()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya existe una cuenta con este número");
		} else {
			acc.setUserId(session.get().getUserId());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		}
	}
	
	@PutMapping(path="/{accNumber}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> editAccount(@Valid @RequestBody Account acc, @PathVariable("accNumber") long accNumber, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (accRepo.findByAccNumberAndUserId(acc.getNumber(), session.get().getUserId()).isPresent()) {
			acc.setUserId(session.get().getUserId());
			Account newAcc = accRepo.save(acc);
			return ResponseEntity.ok(newAcc);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{accNumber}")
	public ResponseEntity<?> deleteAccount(@PathVariable("accNumber") long accNumber, HttpServletRequest req) {
		String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
		Optional<Session> session = sessionsRepo.findById(jwtService.getSessionId(authorization));
		
		if (accRepo.findByAccNumberAndUserId(accNumber, session.get().getUserId()).isPresent()) {
			accRepo.deleteById(accNumber);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
