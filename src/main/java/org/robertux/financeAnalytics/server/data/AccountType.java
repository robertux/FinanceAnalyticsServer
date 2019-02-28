package org.robertux.financeAnalytics.server.data;

public class AccountType extends KeyValue {
	
	public static final AccountType SAVINGS = new AccountType("SAV", "Savings");
	public static final AccountType CHECKING = new AccountType("CHK", "Checking");
	public static final AccountType LOAN = new AccountType("LAN", "Loan");
	public static final AccountType CREDIT_CARD = new AccountType("CRD", "Credit card");
	public static final AccountType CASH = new AccountType("CSH", "Cash");
	
	private AccountType(String key, String value) {
		super(key, value);
	}
	
	public AccountType getDefault() {
		return SAVINGS;
	}
	
	public AccountType[] getAll() {
		return new AccountType[] {SAVINGS, CHECKING, LOAN, CREDIT_CARD, CASH};
	}
}
