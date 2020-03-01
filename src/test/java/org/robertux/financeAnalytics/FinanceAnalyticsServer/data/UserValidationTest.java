package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.data.UserStatus;
import org.robertux.financeAnalytics.server.data.entities.User;

public class UserValidationTest extends DataValidationTest {
	private User user;

	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setId(1l);
		user.setName("test1");
		user.setPassword("password");
		user.setStatus(UserStatus.ACTIVE.getCode());
	}

	@After
	public void tearDown() throws Exception {
		user = null;
	}

	@Test
	public void testValid() {
		Set<ConstraintViolation<User>> violations = this.validator.validate(this.user);
		assertEquals("No deben existir errores de validacion: " + violations.stream().map(v -> v.getMessage()).reduce("", String::concat), 0, violations.size());
	}

	@Test
	public void testInvalidId() {
		user.setId(-1l);
		
		Set<ConstraintViolation<User>> violations = this.validator.validate(this.user);
		assertNotEquals("Id debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidName() {
		user.setName(" ");
		
		Set<ConstraintViolation<User>> violations = this.validator.validate(this.user);
		assertNotEquals("Nombre debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidStatus() {
		user.setStatus("ABC");
		
		Set<ConstraintViolation<User>> violations = this.validator.validate(this.user);
		assertNotEquals("Status debe ser invalido", 0l, violations.size());
	}
}
