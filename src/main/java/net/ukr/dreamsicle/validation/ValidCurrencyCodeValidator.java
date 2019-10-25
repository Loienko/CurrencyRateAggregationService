package net.ukr.dreamsicle.validation;


import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Currency;

/**
 * ValidCurrencyCodeValidator class that implements {@link ConstraintValidator} for class {@link ValidCurrencyCode}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Slf4j
public class ValidCurrencyCodeValidator implements ConstraintValidator<ValidCurrencyCode, String> {

    /**
     * gets validate currency code
     *
     * @param value
     * @param constraintValidatorContext
     * @return Boolean value of the verified currency code data
     */
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