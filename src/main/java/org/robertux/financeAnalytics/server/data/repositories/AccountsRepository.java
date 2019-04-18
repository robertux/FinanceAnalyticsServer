package org.robertux.financeAnalytics.server.data.repositories;

import java.util.List;
import java.util.Optional;

import org.robertux.financeAnalytics.server.data.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountsRepository extends CrudRepository<Account, Long> {

	@Query("SELECT c FROM Account c WHERE c.userId = :userId ORDER BY c.number")
	public List<Account> findAllByUserId(@Param("userId") long userId);
	
	@Query("SELECT c FROM Account c WHERE c.userId = :userId AND c.number = :accNumber")
	public Optional<Account> findByAccNumberAndUserId(@Param("accNumber") long accNum, @Param("userId") long userId);
}
