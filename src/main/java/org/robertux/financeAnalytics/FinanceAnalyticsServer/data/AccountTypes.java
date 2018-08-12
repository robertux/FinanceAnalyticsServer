package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AccountTypes {
	SAVINGS(1, "Savings"),
	CHECKING(2, "Checking"),
	LOAN(3, "Loan"),
	CREDIT_CARD(4, "Credit card"),
	CASH(5, "Cash");

	private int code;
	private String description;

	private AccountTypes(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static AccountTypes get(int code) {
		return Arrays.stream(AccountTypes.values()).filter(acct -> acct.getCode() == code).findFirst().orElse(AccountTypes.SAVINGS);
	}

	public static List<String> listNames() {
		return Arrays.stream(AccountTypes.values()).map(acct -> acct.getDescription()).collect(Collectors.toList());
	}

	public int getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
