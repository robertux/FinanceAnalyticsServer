package org.robertux.financeAnalytics.server.data.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.robertux.financeAnalytics.server.data.SessionStatus;
import org.robertux.financeAnalytics.server.data.validators.ValidSessionStatus;

@Entity
@Table(name="sessions")
public class Session {
	
	@Id
	@NotBlank(message = "ID must not be blank")
	private String id;
	
	@Column(name="user_id")
	@Min(value = 0, message = "User ID must ge greater or equal to zero")
	private long userId;
	
	@Column(name="user_ip")
	@Size(min=0, max=255, message="User IP must be between 0 and 255 characters")
	private String userIp;
	
	@Column(name="created_at")
	@NotNull(message = "Created At must not be null")
	private Date createdAt;
	
	@Column(name="updated_at")
	private Date updatedAt;
	
	@ValidSessionStatus
	private String status;
	
	public Session() {
		
	}
	
	public Session(String id, long userId) {
		this.userId = userId;
		this.id = id;
		this.createdAt = new Date();
		this.status = SessionStatus.ACTIVE.getCode();
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
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", userId=" + userId + ", userIp=" + userIp + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", status=" + status + "]";
	}
}
