package net.ukr.dreamsicle.validation;


import com.google.common.base.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;
import java.util.Set;

public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    private Boolean isOptional;

    @Override
    public void initialize(ValidCurrencyCode validCurrencyCode) {
        this.isOptional = validCurrencyCode.optional();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsIsoCode = false;

        Set<Currency> currencies = Currency.getAvailableCurrencies();

        try {
            containsIsoCode = currencies.contains(Currency.getInstance(value));
        } catch (IllegalArgumentException e) {
            e.getStackTrace();
        }

        return Boolean.TRUE.equals(isOptional) ? (containsIsoCode || (Strings.isNullOrEmpty(value))) : containsIsoCode;
    }
}
