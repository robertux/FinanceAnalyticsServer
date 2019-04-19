package org.robertux.financeAnalytics.server.data.repositories;

import java.util.Optional;

import org.robertux.financeAnalytics.server.data.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.name = :name")
	public Optional<User> findByName(@Param("name") String name);
}
