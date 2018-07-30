package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessage;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.DataSourceConfigurator;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.User;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.records.UserRecord;

public class UsersRepository {
	private Logger logger = LogManager.getLogger(this.getClass());
	private DataSourceConfigurator config = new DataSourceConfigurator();
	
	public UserRecord get(String name) {
		try (Connection cn = config.getConnection()) {
			DSLContext ctx = DSL.using(cn, SQLDialect.POSTGRES_9_5);
			Result<Record> result = ctx.select().from(User.USER).where(User.USER.NAME.eq(name)).fetch();
			
			return result.isEmpty()? null: (UserRecord)result.get(0);
					
		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción buscando el usuario con nombre {}", name), ex);
			return null;
		}
	}
}
