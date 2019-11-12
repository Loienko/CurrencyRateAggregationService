package net.ukr.dreamsicle.validation.country;


import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;

@Slf4j
public class ValidCountry implements ConstraintValidator<CountryValidator, String> {

    private static final String ILLEGAL_COUNTRY = "Illegal Country";

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        boolean containsCountry = false;
        try {
            containsCountry = Arrays.stream(Locale.getAvailableLocales())
                    .anyMatch(list -> list.getDisplayCountry().equals(country));
        } catch (IllegalArgumentException | NullPointerException ignored) {
            log.info(ILLEGAL_COUNTRY);
        }

        return !Strings.isNullOrEmpty(country) && containsCountry;
    }
}
