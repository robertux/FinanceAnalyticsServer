package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
	public static final String CATEGORY_DEFAULT = "DEFAULT";

	@Id
	@NotBlank(message = "Name must not be blank")
	@Size(max=20, message = "Name must not be greater than 20 characters")
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
