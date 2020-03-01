package org.robertux.financeAnalytics.server.data.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.robertux.financeAnalytics.server.data.SessionStatus;

public class SessionStatusValidator implements ConstraintValidator<ValidSessionStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return SessionStatus.ACTIVE.get(value).isPresent();
	}

}
