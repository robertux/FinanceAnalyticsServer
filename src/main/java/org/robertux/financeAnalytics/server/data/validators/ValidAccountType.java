package org.robertux.financeAnalytics.server.data.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = AccountTypeValidator.class)
public @interface ValidAccountType {

	String message() default "Account type must be valid";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
