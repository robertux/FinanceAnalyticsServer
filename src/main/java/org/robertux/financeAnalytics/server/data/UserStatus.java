package org.robertux.financeAnalytics.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserStatus extends KeyValue {
	
	@JsonIgnore public static final UserStatus ACTIVE = new UserStatus("A", "Active");
	@JsonIgnore public static final UserStatus INACTIVE = new UserStatus("I", "Inactive");
	@JsonIgnore public static final UserStatus SUSPENDED = new UserStatus("S", "Suspended");
	
	private UserStatus(String key, String value) {
		super(key, value);
	}
	
	@JsonIgnore 
	public UserStatus getDefault() {
		return ACTIVE;
	}
	
	@JsonIgnore 
	public UserStatus[] getAll() {
		return new UserStatus[] {ACTIVE, INACTIVE, SUSPENDED};
	}
}
