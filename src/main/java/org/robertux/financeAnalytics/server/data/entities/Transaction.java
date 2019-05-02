package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.robertux.financeAnalytics.server.data.validators.ValidCurrency;
import org.robertux.financeAnalytics.server.data.validators.ValidTransactionStatus;


/**
 * The persistent class for the transaction database table.
 *
 */
@Entity
@Table(name="transactions")
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String CATEGORY_DEFAULT = "DEFAULT";

	@Id
	@NotBlank
	private String id;

	@NotNull
	private BigDecimal amount;

	@Column(name="category_name")
	@NotBlank
	@Size(max=20)
	private String categoryName;

	@Column(name="date_time")
	@NotNull
	private Date date;

	@NotNull
	@Size(max=50)
	private String title;

	@Size(max=255)
	private String description;

	@Size(max=20)
	private String reference;
	
	@ValidCurrency
	private String currency;

	@Column(name="account_number") 
	@Min(value = 0)
	private long accountNumber;
	
	@ValidTransactionStatus
	private String status;

	public Transaction() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		//Se asegura que los montos de las transacciones sean de dos decimales
		this.amount = amount != null? amount.setScale(2, RoundingMode.HALF_EVEN): amount;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		//Se asegura que si la categoría viene vacía, se asigne una por defecto
		this.categoryName = (categoryName == null || categoryName.trim().isEmpty()? CATEGORY_DEFAULT: categoryName);
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		//Se asegura que si la fecha viene vacía, se asigne la fecha y hora actual
		this.date = date == null? new Date(): date;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReference() {
		return this.reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
