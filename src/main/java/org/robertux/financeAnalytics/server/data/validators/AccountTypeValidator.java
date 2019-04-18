package org.robertux.financeAnalytics.server.data.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.robertux.financeAnalytics.server.data.AccountType;

public class AccountTypeValidator implements ConstraintValidator<ValidAccountType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return AccountType.SAVINGS.get(value).isPresent();
	}

}
