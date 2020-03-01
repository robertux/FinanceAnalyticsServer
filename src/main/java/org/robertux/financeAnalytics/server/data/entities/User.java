package org.robertux.financeAnalytics.server.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.robertux.financeAnalytics.server.data.validators.ValidUserStatus;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Min(value = 0)
	private Long id;

	@NotBlank
	@Size(min=5, max=255, message="Name must be between 5 and 255 characters")
	private String name;

	@Size(min=5, max=255, message="Password must be between 5 and 255 characters")
	private String password;

	@ValidUserStatus
	private String status;

	public User() {
	}

	public User(Long id, String name, String password, String status) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.status = status;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=****, status=" + status + "]";
	}
}
