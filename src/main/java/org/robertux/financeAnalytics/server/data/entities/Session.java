package org.robertux.financeAnalytics.server.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sessions")
public class Session {
	public static int SESSION_MAX_INACTIVE_SECS = 5;
	
	@Id
	private String id;
	
	@Column(name="user_id")
	private long userId;
	
	@Column(name="created_at")
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
