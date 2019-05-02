package org.robertux.financeAnalytics.server.data.repositories;

import java.util.Optional;

import org.robertux.financeAnalytics.server.data.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<Category, String> {

	@Query("SELECT c FROM Category c WHERE c.name = :name")
	public Optional<Category> findByName(@Param("name") String name);
}
