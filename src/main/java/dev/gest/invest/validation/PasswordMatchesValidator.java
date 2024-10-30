package dev.gest.invest.validation;

import dev.gest.invest.dto.RegisterUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterUserDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(RegisterUserDto user, ConstraintValidatorContext context) {
        return user.getPassword() != null && user.getPassword().equals(user.getConfirmation());
    }

}
