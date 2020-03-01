package org.robertux.financeAnalytics.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TransactionStatus extends KeyValue {
	
	@JsonIgnore public static final TransactionStatus APPLIED = new TransactionStatus("A", "Applied");
	@JsonIgnore public static final TransactionStatus REJECTED = new TransactionStatus("R", "Rejected");
	@JsonIgnore public static final TransactionStatus FAILED = new TransactionStatus("F", "Failed");
	@JsonIgnore public static final TransactionStatus PENDING = new TransactionStatus("P", "Pending");
	@JsonIgnore public static final TransactionStatus CANCELLED = new TransactionStatus("C", "Cancelled");
	
	private TransactionStatus(String key, String value) {
		super(key, value);
	}
	
	@JsonIgnore 
	public TransactionStatus getDefault() {
		return APPLIED;
	}
	
	@JsonIgnore 
	public TransactionStatus[] getAll() {
		return new TransactionStatus[] {APPLIED, REJECTED, FAILED, PENDING};
	}
}
