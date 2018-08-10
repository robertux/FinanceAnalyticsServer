package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

}
