package org.robertux.financeAnalytics.FinanceAnalyticsServer.controllers;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.records.AccountRecord;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories.AccountsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

	@GetMapping("/users/{userId}/accounts")
	public List<AccountRecord> getAccounts(@PathVariable("userId") long userId) {
		AccountsRepository accRepo = new AccountsRepository();
		
		return accRepo.getAll(userId);
	}
}
