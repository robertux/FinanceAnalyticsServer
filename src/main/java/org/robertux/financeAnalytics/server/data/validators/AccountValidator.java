package org.robertux.financeAnalytics.server.data.validators;

import org.robertux.financeAnalytics.server.data.AccountType;
import org.robertux.financeAnalytics.server.data.entities.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Account.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Account acc = (Account)target;
		
		if (acc.getNumber() <= 0) {
			errors.rejectValue("number", "Must be valid");
		}
		
		if (!AccountType.SAVINGS.get(acc.getType()).isPresent()) {
			errors.rejectValue("type", "Must be valid");
		}
		
		
		if (acc.getUserId() <= 0) {
			errors.rejectValue("userId", "Must belong to a user");
		}
	}

}
