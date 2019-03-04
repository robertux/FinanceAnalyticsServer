package org.robertux.financeAnalytics.server.data.validators;

import java.util.Currency;

import org.robertux.financeAnalytics.server.data.TransactionStatus;
import org.robertux.financeAnalytics.server.data.entities.Transaction;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Transaction trn = (Transaction)target;
		
		if (trn.getAccountNumber() <= 0) {
			errors.rejectValue("accountNumber", "Must be valid");
		}
		
		if (!TransactionStatus.APPLIED.get(trn.getStatus()).isPresent()) {
			errors.rejectValue("status", "Must be valid");
		}
		
		try {
			Currency.getInstance(trn.getCurrency());
		} catch (IllegalArgumentException ex) {
			errors.rejectValue("currency", "Must be valid");
		}

	}

}
