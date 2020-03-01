package org.robertux.financeAnalytics.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountStatus extends KeyValue {
	
	@JsonIgnore public static final AccountStatus ACTIVE = new AccountStatus("A", "Active");
	@JsonIgnore public static final AccountStatus INACTIVE = new AccountStatus("I", "Inactive");
	@JsonIgnore public static final AccountStatus SUSPENDED = new AccountStatus("S", "Suspended");
	@JsonIgnore public static final AccountStatus CLOSED = new AccountStatus("C", "Closed");
	
	private AccountStatus(String key, String value) {
		super(key, value);
	}
	
	@JsonIgnore 
	public AccountStatus getDefault() {
		return ACTIVE;
	}
	
	@JsonIgnore 
	public AccountStatus[] getAll() {
		return new AccountStatus[] {ACTIVE, INACTIVE, CLOSED};
	}
}
