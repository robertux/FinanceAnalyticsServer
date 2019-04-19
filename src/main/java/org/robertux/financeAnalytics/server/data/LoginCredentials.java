package org.robertux.financeAnalytics.server.data;

import javax.validation.constraints.NotBlank;

public class LoginCredentials {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
