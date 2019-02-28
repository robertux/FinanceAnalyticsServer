package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.robertux.financeAnalytics.server.data.AccountType;


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

	private long userId;

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
		this.type = AccountType.get(type).getCode();
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}