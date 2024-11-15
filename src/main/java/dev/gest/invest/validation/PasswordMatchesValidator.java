package dev.gest.invest.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    private String passwordField;
    private String confirmationField;

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        this.passwordField = passwordField;
        this.confirmationField = confirmationField;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);

        Object password = beanWrapper.getPropertyValue(passwordField);
        Object confirmation = beanWrapper.getPropertyValue(confirmationField);

        if (password != null && confirmation != null) {
            return password.equals(confirmation);
        }

        return false;
    }

}
