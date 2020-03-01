package org.robertux.financeAnalytics.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SessionStatus extends KeyValue {
	
	@JsonIgnore public static final SessionStatus ACTIVE = new SessionStatus("A", "Active");
	@JsonIgnore public static final SessionStatus INACTIVE = new SessionStatus("I", "Inactive");
	
	private SessionStatus(String key, String value) {
		super(key, value);
	}
	
	@JsonIgnore 
	public SessionStatus getDefault() {
		return ACTIVE;
	}
	
	@JsonIgnore 
	public SessionStatus[] getAll() {
		return new SessionStatus[] {ACTIVE, INACTIVE};
	}
}
