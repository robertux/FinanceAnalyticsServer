package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.entities;

import java.io.Serializable;
import javax.persistence.*;

import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.AccountTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


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

	private Integer type;

	@JsonIgnore
	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	@JsonIgnore
	//bi-directional many-to-one association to Transaction
	@OneToMany(mappedBy="account")
	private List<Transaction> transactions;

	public Account() {
	}

	public Long getNumber() {
		return this.number;
	}

	public void setNumber(Long number) {
		//Se asegura que el número de cuenta sea siempre positivo
		this.number = Math.abs(number);
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		//Se asegura que el tipo asignado corresponda con uno de los tipos de cuenta válidos
		this.type = AccountTypes.get(type).getCode();
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Transaction addTransaction(Transaction transaction) {
		getTransactions().add(transaction);
		transaction.setAccount(this);

		return transaction;
	}

	public Transaction removeTransaction(Transaction transaction) {
		getTransactions().remove(transaction);
		transaction.setAccount(null);

		return transaction;
	}

}