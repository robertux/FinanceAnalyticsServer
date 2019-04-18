package org.robertux.financeAnalytics.server.data.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.robertux.financeAnalytics.server.data.UserStatus;

public class UserStatusValidator implements ConstraintValidator<ValidUserStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return UserStatus.ACTIVE.get(value).isPresent();
	}

}
