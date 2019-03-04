package org.robertux.financeAnalytics.server.data.repositories;

import java.util.List;

import org.robertux.financeAnalytics.server.data.entities.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SessionsRepository extends CrudRepository<Session, String> {

	@Query("SELECT s FROM Session s WHERE s.userId = :userID")
	public List<Session> findByUserId(@Param("userId") long userId);
}
