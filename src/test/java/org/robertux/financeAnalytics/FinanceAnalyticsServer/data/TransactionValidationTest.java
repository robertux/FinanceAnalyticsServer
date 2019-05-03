package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.data.TransactionStatus;
import org.robertux.financeAnalytics.server.data.entities.Account;
import org.robertux.financeAnalytics.server.data.entities.Transaction;

public class TransactionValidationTest extends DataValidationTest {
	private Transaction trans;
	private Logger logger;

	@Before
	public void setUp() throws Exception {
		trans = new Transaction();
		trans.setId("abc");
		trans.setAccount(new Account());
		trans.setAmount(BigDecimal.ONE);
		trans.setCategoryName("transcat");
		trans.setCurrency("USD");
		trans.setStatus(TransactionStatus.APPLIED.getCode());
		trans.setDate(new Date());
		trans.setTitle("trans title");
		
		logger = LogManager.getLogger(this.getClass());
	}

	@After
	public void tearDown() throws Exception {
		this.trans = null;
	}

	@Test
	public void testValid() {
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		violations.forEach(v -> logger.debug(v.getConstraintDescriptor().getAnnotation() + " " + v.getMessage() + " " + v.getInvalidValue()));
		assertEquals("No deben existir errores de validacion", 0, violations.size());
	}

	@Test
	public void testInvalidId() {
		trans.setId(null);
		
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		assertNotEquals("Id debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidAmount() {
		trans.setAmount(null);
		
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		assertNotEquals("Monto debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidCurrency() {
		trans.setCurrency("ASDF");
		
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		assertNotEquals("Moneda debe ser invalida", 0l, violations.size());
	}
	
	@Test
	public void testInvalidAccountNumber() {
		trans.setAccount(null);
		
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		assertNotEquals("Cuenta debe ser invalida", 0l, violations.size());
	}
	
	@Test
	public void testInvalidStatus() {
		trans.setStatus("XYZ");
		
		Set<ConstraintViolation<Transaction>> violations = this.validator.validate(this.trans);
		assertNotEquals("Estatus debe ser invalido", 0l, violations.size());
	}
}
