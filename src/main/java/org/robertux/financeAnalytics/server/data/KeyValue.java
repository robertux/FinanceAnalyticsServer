package org.robertux.financeAnalytics.server.data;

import java.util.Arrays;
import java.util.Optional;

public abstract class KeyValue {
	private String code;
	private String description;
	
	protected KeyValue(String code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public abstract KeyValue getDefault();
	
	public abstract KeyValue[] getAll();
	
	public KeyValue getAny(String key) {
		return this.get(key).orElse(getDefault());
	}
	
	public Optional<KeyValue> get(String key) {
		return Arrays.stream(getAll()).filter(kv -> kv.code.equals(key)).findAny();
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
}
