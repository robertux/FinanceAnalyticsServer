package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank
	@Size(max=20)
	private String name;

	public Category() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
