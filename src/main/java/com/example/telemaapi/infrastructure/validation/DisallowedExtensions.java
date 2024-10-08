package com.example.telemaapi.infrastructure.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AllowedExtensionsValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DisallowedExtensions {
	String message() default "Invalid file type";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] value();
}