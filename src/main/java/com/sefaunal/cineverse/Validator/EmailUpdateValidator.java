package com.sefaunal.cineverse.Validator;

import com.sefaunal.cineverse.Annotation.EmailUpdate;
import com.sefaunal.cineverse.Service.UserService;
import com.sefaunal.cineverse.Utils.CommonUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author github.com/sefaunal
 * @since 2025-04-10
 */
@RequiredArgsConstructor
public class EmailUpdateValidator implements ConstraintValidator<EmailUpdate, String> {
    private final UserService userService;

    @Override
    public void initialize(EmailUpdate constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.equals(userService.findUserByUsername(CommonUtils.getUserInfo()).getEmail())) {
            return true;
        }
        return userService.isEmailFree(email);
    }
}
