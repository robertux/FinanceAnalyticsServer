package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

public class Currency {
	private String name;
	private String symbol;
	
	public static final Currency DEFAULT = new Currency("DEFAULT", "D"); 
	
	public Currency(String name, String symbol) {
		super();
		this.name = name;
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	
}
