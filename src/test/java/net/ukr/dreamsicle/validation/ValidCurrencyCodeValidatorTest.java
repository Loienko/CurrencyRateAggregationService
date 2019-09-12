package net.ukr.dreamsicle.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
class ValidCurrencyCodeValidatorTest {

    @InjectMocks
    private ValidCurrencyCodeValidator validCurrencyCodeValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsValidPositive() {
        String value = "UAH";

        boolean valid = validCurrencyCodeValidator.isValid(value, any());

        assertTrue(valid);
    }

    @Test
    void testIsValidEmptyInputValueField() {
        String value = "";

        assertThrows(NullPointerException.class, () -> validCurrencyCodeValidator.isValid(value, any()));
    }

    @Test
    void testIsValidNullInputValueField() {
        assertThrows(NullPointerException.class, () -> validCurrencyCodeValidator.isValid(null, any()));
    }

    @Test
    void testIsValidNotCorrectInputValueField() {
        String value = "UAH1";

        assertThrows(IllegalArgumentException.class, () -> validCurrencyCodeValidator.isValid(value, any()));
    }
}