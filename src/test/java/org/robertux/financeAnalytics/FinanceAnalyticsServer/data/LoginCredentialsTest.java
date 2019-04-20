package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.data.LoginCredentials;

public class LoginCredentialsTest extends DataValidationTest {
	private LoginCredentials credentials;
	private Logger logger;
	

	@Before
	public void setUp() {
		this.credentials = new LoginCredentials();
		this.logger = LogManager.getLogger(this.getClass());
	}

	@After
	public void tearDown() {
		this.credentials = null;
	}

	@Test
	public void testValid() {
		this.logger.debug("validator: " + this.validator);
		this.logger.debug("credentials: " + this.credentials);
		this.credentials.setName("name");
		this.credentials.setPassword("password");
		Set<ConstraintViolation<LoginCredentials>> violations = this.validator.validate(this.credentials);
		assertEquals("No deben existir errores de validacion", 0, violations.size());
	}
	
	@Test
	public void testInvalid() {
		this.credentials.setName(null);
		this.credentials.setPassword(null);
		Set<ConstraintViolation<LoginCredentials>> violations = this.validator.validate(this.credentials);
		assertNotEquals("Deben existir errores de validacion", 0, violations.size());
	}

}
