package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.util.List;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository extends CrudRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.account.number = :accountNumber")
	public List<Transaction> findAllByAccountNum(@Param("accountNumber") long accountNumber, Pageable pg);
	
	@Query("SELECT t FROM Transaction t WHERE t.account.user.id = :userId")
	public List<Transaction> findAllByUser(@Param("userId") long userId, Pageable pg);
}
