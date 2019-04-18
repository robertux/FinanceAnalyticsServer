package org.robertux.financeAnalytics.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountType extends KeyValue {
	
	@JsonIgnore public static final AccountType SAVINGS = new AccountType("SAV", "Savings");
	@JsonIgnore public static final AccountType CHECKING = new AccountType("CHK", "Checking");
	@JsonIgnore public static final AccountType LOAN = new AccountType("LAN", "Loan");
	@JsonIgnore public static final AccountType CREDIT_CARD = new AccountType("CRD", "Credit card");
	@JsonIgnore public static final AccountType CASH = new AccountType("CSH", "Cash");
	
	private AccountType(String key, String value) {
		super(key, value);
	}
	
	@JsonIgnore
	public AccountType getDefault() {
		return SAVINGS;
	}
	
	@JsonIgnore
	public AccountType[] getAll() {
		return new AccountType[] {SAVINGS, CHECKING, LOAN, CREDIT_CARD, CASH};
	}
}
