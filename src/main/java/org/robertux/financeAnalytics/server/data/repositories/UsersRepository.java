package org.robertux.financeAnalytics.server.data.repositories;

import java.util.Optional;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.robertux.financeAnalytics.server.data.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.name = :name")
	public Optional<User> findByName(@Param("name") String name);
	
	public default String encrypt(String input) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		return encryptor.encryptPassword(input);
	}
	
	public default boolean isValid(String input, String encrypted) {
		StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
		
		return encryptor.checkPassword(input, encrypted);
	}
}
