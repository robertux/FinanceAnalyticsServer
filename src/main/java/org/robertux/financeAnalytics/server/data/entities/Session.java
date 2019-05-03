package org.robertux.financeAnalytics.server.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="sessions")
public class Session {
	
	@Id
	@NotBlank(message = "ID must not be blank")
	private String id;
	
	@Column(name="user_id")
	@Min(value = 0, message = "User ID must ge greater or equal to zero")
	private long userId;
	
	@Column(name="created_at")
	@NotNull(message = "Created At must not be null")
	private Date createdAt;
	
	public Session() {
		
	}
	
	public Session(String id, long userId) {
		this.userId = userId;
		this.id = id;
		this.createdAt = new Date();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
