package com.sefaunal.cineverse.Validator;

import com.sefaunal.cineverse.Annotation.UniqueUsername;
import com.sefaunal.cineverse.Service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author github.com/sefaunal
 * @since 2025-01-11
 */
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUsernameFree(username);
    }
}