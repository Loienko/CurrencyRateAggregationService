package net.ukr.dreamsicle.validation;


import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;

public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !Strings.isNullOrEmpty(value) && Currency.getAvailableCurrencies().contains(Currency.getInstance(value));
    }
}
