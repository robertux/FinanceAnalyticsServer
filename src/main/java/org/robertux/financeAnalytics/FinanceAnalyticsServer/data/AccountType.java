package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import java.util.Arrays;

public class AccountType {
	private int code;
	private String description;
	
	public static final AccountType SAVINGS = new AccountType(1, "Savings");
	public static final AccountType CHECKING = new AccountType(2, "Checking");
	public static final AccountType LOAN = new AccountType(3, "Loan");
	public static final AccountType CREDIT_CARD = new AccountType(4, "Credit card");
	public static final AccountType CASH = new AccountType(5, "Cash");
	
	public static final AccountType[] values = {SAVINGS, CHECKING, LOAN, CREDIT_CARD, CASH};
	
	public AccountType(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public static AccountType get(int code) {
		return Arrays.stream(values).filter(acct -> acct.getCode() == code).findFirst().orElse(SAVINGS);
	}

	public int getCode() {
		return this.code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
