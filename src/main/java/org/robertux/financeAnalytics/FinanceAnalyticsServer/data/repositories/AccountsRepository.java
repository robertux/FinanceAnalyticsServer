package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.message.MessageFormatMessage;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.Account;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.records.AccountRecord;

public class AccountsRepository extends BaseRepository<AccountRecord> {

	public List<AccountRecord> getAll(long usrId) {
		List<AccountRecord> accounts = new ArrayList<>(0);
		try (Connection cn = config.getConnection()) {
			accounts.addAll(getContext(cn).selectFrom(Account.ACCOUNT).where(Account.ACCOUNT.USER_ID.eq(usrId)).orderBy(Account.ACCOUNT.ALIAS).fetch());

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción obteniendo la lista de cuentas para el usuario con ID {}", usrId), ex);
		}
		return accounts;
	}
	
	public AccountRecord get(long accountNumber) {
		AccountRecord acc = null;
		try (Connection cn = config.getConnection()) {
			acc = getContext(cn).selectFrom(Account.ACCOUNT).where(Account.ACCOUNT.NUMBER.eq(accountNumber)).fetchOne();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción obteniendo cuenta con número {}", accountNumber), ex);
		}
		return acc;
	}
}
