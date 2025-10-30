package ru.maybetm;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserDtoCorrectlyValidator implements ConstraintValidator<UserDtoCorrectly, UserDto> {

    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {

        if (value.name().equals("test-user"))
            throw new IllegalArgumentException("Incorrect user name %s".formatted(value.name()));

        return true;
    }
}
