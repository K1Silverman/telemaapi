package com.example.telemaapi.infrastructure.validation;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class AllowedExtensionsValidator implements ConstraintValidator<DisallowedExtensions, MultipartFile> {

	private List<String> disallowedExtensions;

	@Override
	public void initialize(DisallowedExtensions constraintAnnotation) {
		this.disallowedExtensions = List.of(constraintAnnotation.value());
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if (file == null || file.isEmpty()) {
			return true;
		}
		String filetype = file.getContentType();
		return !disallowedExtensions.contains(filetype);
	}
}
