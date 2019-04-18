package org.robertux.financeAnalytics.server.data.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.robertux.financeAnalytics.server.data.TransactionStatus;

public class TransactionStatusValidator implements ConstraintValidator<ValidTransactionStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return TransactionStatus.APPLIED.get(value).isPresent();
	}

}
