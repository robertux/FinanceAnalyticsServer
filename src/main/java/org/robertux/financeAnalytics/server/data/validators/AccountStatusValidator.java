package org.robertux.financeAnalytics.server.data.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.robertux.financeAnalytics.server.data.AccountStatus;

public class AccountStatusValidator implements ConstraintValidator<ValidAccountStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return AccountStatus.ACTIVE.get(value).isPresent();
	}

}
