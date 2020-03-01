package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.robertux.financeAnalytics.server.data.validators.ValidAccountStatus;
import org.robertux.financeAnalytics.server.data.validators.ValidAccountType;
import org.robertux.financeAnalytics.server.data.validators.ValidCurrency;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Min(value = 0, message = "Account number must be greater or equal to zero")
	private Long number;
	
	@Transient
	private String numberMask;

	@NotBlank(message = "Alias must not be blank")
	@Size(max=50, message = "Alias must not be greater than 50 characters")
	private String alias;

	@ValidAccountType(message = "Type must be valid")
	private String type;

	@Column(name="user_id") 
	@Min(value = 0, message = "User ID must be greater or equal to zero")
	private long userId;
	
	@ValidCurrency(message = "Currency must be valid")
	private String currency;

	@NotNull(message = "Balance must not be null")
	private BigDecimal balance;
	
	@ValidAccountStatus
	private String status;

	public Account() {
		
	}

	public Account(Long number, String numberMask, String alias, String type, long userId, String currency, BigDecimal balance, String status) {
		super();
		this.number = number;
		this.numberMask = numberMask;
		this.alias = alias;
		this.type = type;
		this.userId = userId;
		this.currency = currency;
		this.balance = balance;
		this.status = status;
	}

	public Long getNumber() {
		return this.number;
	}

	public void setNumber(Long number) {
		this.number = number;
		
		if (number == 0 || number == null) {
			this.setNumberMask("****");
		} else {
			String numberStr = number.toString();
			this.setNumberMask("****".concat(numberStr.length() > 4? numberStr.substring(numberStr.length() - 4): numberStr));
		}
	}

	public String getNumberMask() {
		return numberMask;
	}

	public void setNumberMask(String numberMask) {
		this.numberMask = numberMask;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + ", numberMask=" + numberMask + ", alias=" + alias + ", type=" + type + ", userId=" + userId + ", currency=" + currency + ", balance=" + balance + ", status=" + status + "]";
	}
}
