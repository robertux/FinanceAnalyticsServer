package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.data.SessionStatus;
import org.robertux.financeAnalytics.server.data.entities.Session;

public class SessionValidationTest extends DataValidationTest {
	private Session session;

	@Before
	public void setUp() throws Exception {
		this.session = new Session();
		this.session.setId("abc");
		this.session.setUserId(1l);
		this.session.setCreatedAt(new Date());
		this.session.setStatus(SessionStatus.ACTIVE.getCode());
	}

	@After
	public void tearDown() throws Exception {
		this.session = null;
	}

	@Test
	public void testValid() {
		Set<ConstraintViolation<Session>> violations = this.validator.validate(this.session);
		assertEquals("No deben existir errores de validacion: " + violations.stream().map(v -> v.getMessage()).reduce("", String::concat), 0, violations.size());
	}
	
	@Test
	public void testInvalidId() {
		this.session.setId("");
		Set<ConstraintViolation<Session>> violations = this.validator.validate(this.session);
		assertNotEquals("ID debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidUserId() {
		this.session.setUserId(-1l);
		Set<ConstraintViolation<Session>> violations = this.validator.validate(this.session);
		assertNotEquals("userId debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidCreatedAt() {
		this.session.setCreatedAt(null);
		Set<ConstraintViolation<Session>> violations = this.validator.validate(this.session);
		assertNotEquals("createdAt debe ser invalido", 0l, violations.size());
	}

}
