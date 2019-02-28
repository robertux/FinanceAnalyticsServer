package org.robertux.financeAnalytics.server.data;

public class UserStatus extends KeyValue {
	
	public static final UserStatus ACTIVE = new UserStatus("A", "Active");
	public static final UserStatus INACTIVE = new UserStatus("I", "Inactive");
	
	private UserStatus(String key, String value) {
		super(key, value);
	}
	
	public UserStatus getDefault() {
		return ACTIVE;
	}
	
	public UserStatus[] getAll() {
		return new UserStatus[] {ACTIVE, INACTIVE};
	}
}
