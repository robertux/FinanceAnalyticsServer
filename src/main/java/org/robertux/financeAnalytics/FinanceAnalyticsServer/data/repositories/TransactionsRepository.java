package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.message.MessageFormatMessage;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.Transaction;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities.tables.records.TransactionRecord;

public class TransactionsRepository extends BaseRepository<TransactionRecord> {

	public List<TransactionRecord> getAll(long accountNumber) {
		List<TransactionRecord> transactions = new ArrayList<>(0);
		try (Connection cn = config.getConnection()) {
			transactions.addAll(getContext(cn).selectFrom(Transaction.TRANSACTION).where(Transaction.TRANSACTION.ACCOUNT_NUMBER.eq(accountNumber)).orderBy(Transaction.TRANSACTION.DATE.desc()).fetch());

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción obteniendo la lista de transacciones para la cuenta No. {}", accountNumber), ex);
		}
		return transactions;
	}
	
	public TransactionRecord get(long transactionId) {
		TransactionRecord acc = null;
		try (Connection cn = config.getConnection()) {
			acc = getContext(cn).selectFrom(Transaction.TRANSACTION).where(Transaction.TRANSACTION.ID.eq(transactionId)).fetchOne();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción obteniendo transacción con ID {}", transactionId), ex);
		}
		return acc;
	}
}
