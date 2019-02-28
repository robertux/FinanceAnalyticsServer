package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long number;

	private String alias;

	private String type;

	@Column(name="user_id")
	private long userId;
	
	private BigDecimal balance;

	public Account() {
		
	}

	public Long getNumber() {
		return this.number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}