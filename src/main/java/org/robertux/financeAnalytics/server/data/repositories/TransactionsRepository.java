package org.robertux.financeAnalytics.server.data.repositories;

import java.util.List;

import org.robertux.financeAnalytics.server.data.entities.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepository extends CrudRepository<Transaction, String> {

	@Query("SELECT t FROM Transaction t WHERE t.accountNumber = :accountNumber")
	public List<Transaction> findAllByAccountNum(@Param("accountNumber") long accountNumber, Pageable pg);
	
	@Query("SELECT t FROM Transaction t WHERE t.accountNumber in :accNums")
	public List<Transaction> findAllByAccountNums(@Param("accNums") List<Long> accNums, Pageable pg);
}
