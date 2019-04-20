package org.robertux.financeAnalytics.FinanceAnalyticsServer.data;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;

public class DataValidationTest {

	protected ValidatorFactory validatorFactory;
	protected Validator validator;
	
	@Before
	public void baseSetUp() {
		this.validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = this.validatorFactory.getValidator();
	}
	
	@After
	public void baseTearDown() {
		this.validator = null;
		this.validatorFactory = null;
	}
}
