package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	
	private DateFormat dFormat;

	@Id
	@NotBlank(message = "ID must not be null")
	private String id;

	@NotNull(message = "Amount must not be null")
	private BigDecimal amount;

	@Column(name="category_name")
	@NotBlank(message = "Category must not be blank")
	@Size(max=20, message = "Category must not be greater than 20 characters")
	private String categoryName;

	@Column(name="date_time")
	@NotNull(message = "Date must not be null")
	private Date date;
	
	@Transient
	private String dateStr;

	@NotNull(message = "Title must not be null")
	@Size(max=50, message = "Title must not be greater than 50 characters")
	private String title;

	@Size(max=255, message = "Description must not be greater than 255 characters")
	private String description;

	@Size(max=20, message = "Reference must not be greater tan 20 characters")
	private String reference;
	
	@ValidCurrency(message = "Currency must be valid")
	private String currency;
	
	@NotNull(message = "Account must not be null")
	@ManyToOne(optional = false)
	@JoinColumn(name = "account_number", nullable = false, updatable = false, insertable = false)
	private Account account;
	
	@ValidTransactionStatus(message = "Transaction status must be valid")
	private String status;

	public Transaction() {
		this.dFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
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
		this.categoryName = (categoryName == null || categoryName.trim().isEmpty()? Category.CATEGORY_DEFAULT: categoryName);
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		//Se asegura que si la fecha viene vacía, se asigne la fecha y hora actual
		this.date = date == null? new Date(): date;
		
		this.setDateStr(this.dFormat.format(this.date));
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
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

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
