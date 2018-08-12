package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private BigDecimal amount;

	@Column(name="category_name")
	private String categoryName;

	@Column(name="date_time")
	private Date date;

	private String description;

	private String reference;

	@JsonIgnore
	//bi-directional many-to-one association to Account
	@ManyToOne
	private Account account;

	public Transaction() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		//Se asegura que los montos de las transacciones sean de dos decimales
		this.amount = amount.setScale(2, RoundingMode.HALF_EVEN);
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

	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

}
