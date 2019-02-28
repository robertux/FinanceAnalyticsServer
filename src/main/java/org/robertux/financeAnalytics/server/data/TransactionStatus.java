package org.robertux.financeAnalytics.server.data;

public class TransactionStatus extends KeyValue {
	
	public static final TransactionStatus APPLIED = new TransactionStatus("A", "Applied");
	public static final TransactionStatus REJECTED = new TransactionStatus("R", "Rejected");
	public static final TransactionStatus FAILED = new TransactionStatus("F", "Failed");
	public static final TransactionStatus PENDING = new TransactionStatus("P", "Pending");
	
	private TransactionStatus(String key, String value) {
		super(key, value);
	}
	
	public TransactionStatus getDefault() {
		return APPLIED;
	}
	
	public TransactionStatus[] getAll() {
		return new TransactionStatus[] {APPLIED, REJECTED, FAILED, PENDING};
	}
}
