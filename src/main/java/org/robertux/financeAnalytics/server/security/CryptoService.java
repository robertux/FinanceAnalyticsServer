package org.robertux.financeAnalytics.server.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CryptoService {

	public String encrypt(String input) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		return encryptor.encryptPassword(input);
	}
	
	public boolean isValid(String input, String encrypted) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		return encryptor.checkPassword(input, encrypted);
	}
}
