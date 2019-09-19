package net.ukr.dreamsicle.validation;


import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;

@Slf4j
public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsIsoCode = false;
        try {
            containsIsoCode = Currency.getAvailableCurrencies().contains(Currency.getInstance(value));
        } catch (IllegalArgumentException | NullPointerException ignored) {
            log.info("Illegal CurrencyCode");
        }

        return !Strings.isNullOrEmpty(value) && containsIsoCode;
    }
}