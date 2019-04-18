package org.robertux.financeAnalytics.server.data.validators;

import java.util.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		try {
			Currency.getInstance(value);
			return true;
		} catch (IllegalArgumentException ex) {
			return false;
		}
	}

}
