package net.ukr.dreamsicle.validation;

import net.ukr.dreamsicle.validation.currencyCode.ValidCurrencyCodeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
class ValidCurrencyCodeValidatorTest {

    @InjectMocks
    private ValidCurrencyCodeValidator validCurrencyCodeValidator;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsValidPositive() {
        String value = "UAH";

        boolean valid = validCurrencyCodeValidator.isValid(value, constraintValidatorContext);

        assertTrue(valid);
    }

    @Test
    void testIsValidEmptyInputValueField() {
        String value = "";

        boolean valid = validCurrencyCodeValidator.isValid(value, constraintValidatorContext);

        assertFalse(valid);
    }

    @Test
    void testIsValidNullInputValueField() {

        boolean valid = validCurrencyCodeValidator.isValid(null, constraintValidatorContext);

        assertFalse(valid);
    }

    @Test
    void testIsValidNotCorrectInputValueField() {
        String value = "UAH1";

        boolean valid = validCurrencyCodeValidator.isValid(value, constraintValidatorContext);

        assertFalse(valid);
    }
}