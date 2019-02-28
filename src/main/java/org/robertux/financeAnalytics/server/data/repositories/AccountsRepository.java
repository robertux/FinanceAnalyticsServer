package org.robertux.financeAnalytics.server.data.repositories;

import java.util.List;

import org.robertux.financeAnalytics.server.data.entities.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AccountsRepository extends CrudRepository<Account, Long> {

	@Query("SELECT c FROM Account c WHERE c.user.id = :userId ORDER BY c.number")
	public List<Account> findAllByUserId(@Param("userId") long userId);
}
