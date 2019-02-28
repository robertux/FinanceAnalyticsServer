package org.robertux.financeAnalytics.server.data.repositories;

import org.robertux.financeAnalytics.server.data.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

}
