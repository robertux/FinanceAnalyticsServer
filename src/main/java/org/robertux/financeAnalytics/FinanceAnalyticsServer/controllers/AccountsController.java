package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Account;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {
	
	@Autowired
	private AccountsRepository accRepo;

	@GetMapping("/users/{userId}/accounts")
	public List<Account> getAccounts(@PathVariable("userId") long userId) {
		
		return accRepo.findAllByUserId(userId);
	}
}
