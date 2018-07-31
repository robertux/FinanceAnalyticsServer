package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.sql.Connection;

import org.apache.logging.log4j.message.MessageFormatMessage;
import org.jooq.Record;
import org.jooq.Result;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.Sequences;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.User;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.records.UserRecord;

public class UsersRepository extends BaseRepository<UserRecord> {

	public UserRecord get(String name) {
		try (Connection cn = config.getConnection()) {
			Result<Record> result = getContext(cn).select().from(User.USER).where(User.USER.NAME.eq(name)).fetch();

			return result.isEmpty() ? null : (UserRecord) result.get(0);

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción buscando el usuario con nombre {}", name), ex);
			return null;
		}
	}
	
	public int add(UserRecord usr) {
		return this.add(usr, User.USER.ID, Sequences.USER_ID_SEQ);
	}
}
