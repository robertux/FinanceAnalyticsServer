package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robertux.financeAnalytics.server.data.AccountStatus;
import org.robertux.financeAnalytics.server.data.AccountType;
import org.robertux.financeAnalytics.server.data.entities.Account;

public class AccountValidationTest extends DataValidationTest {
	private Account account;

	@Before
	public void setUp() {
		this.account = new Account();
		account.setNumber(1l);
		account.setUserId(1);
		account.setAlias("acc");
		account.setBalance(BigDecimal.ZERO);
		account.setType(AccountType.SAVINGS.getCode());
		account.setCurrency("USD");
		account.setStatus(AccountStatus.ACTIVE.getCode());
	}

	@After
	public void tearDown() throws Exception {
		this.account = null;
	}

	@Test
	public void testValid() {
		Set<ConstraintViolation<Account>> violations = this.validator.validate(this.account);
		assertEquals("No deben existir errores de validacion: " + violations.stream().map(v -> v.getMessage()).reduce("", String::concat), 0, violations.size());
	}
	
	@Test
	public void testInvalidNumber() {
		account.setNumber(-1l);
		
		Set<ConstraintViolation<Account>> violations = this.validator.validate(this.account);
		assertNotEquals("Numero de cuenta debe ser invalida", 0l, violations.size());
	}
	
	@Test
	public void testInvalidType() {
		account.setType("ZZZ");
		
		Set<ConstraintViolation<Account>> violations = this.validator.validate(this.account);
		assertNotEquals("Tipo de cuenta debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidAlias() {
		account.setAlias(null);
		
		Set<ConstraintViolation<Account>> violations = this.validator.validate(this.account);
		assertNotEquals("Alias debe ser invalido", 0l, violations.size());
	}
	
	@Test
	public void testInvalidBalance() {
		account.setBalance(null);
		
		Set<ConstraintViolation<Account>> violations = this.validator.validate(this.account);
		assertNotEquals("Saldo de la cuenta debe ser invalido", 0l, violations.size());
	}

}
