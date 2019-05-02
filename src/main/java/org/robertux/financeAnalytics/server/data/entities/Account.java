package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.robertux.financeAnalytics.server.data.validators.ValidAccountType;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Min(value = 0)
	private Long number;

	@NotBlank
	@Size(max=50)
	private String alias;

	@ValidAccountType
	private String type;

	@Column(name="user_id") 
	@Min(value = 0)
	private long userId;
	
	@ValidCurrency
	private String currency;

	@NotNull
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
	
	public String getCurrency() {
		return this.currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
