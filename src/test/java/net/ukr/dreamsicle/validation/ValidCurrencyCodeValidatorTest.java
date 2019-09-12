package net.ukr.dreamsicle.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class ValidCurrencyCodeValidatorTest {

    @InjectMocks
    ValidCurrencyCodeValidator validCurrencyCodeValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testIsValid() {
        String value = "UAH";

        boolean valid = validCurrencyCodeValidator.isValid(value, any());

        assertTrue(valid);
    }

    @Test
    void testIsValidEmptyValueField(){
        String value = "";

        assertThrows(NullPointerException.class, () -> validCurrencyCodeValidator.isValid(value, any()));
    }

    @Test
    void testIsValidNullValueField(){
        String value = null;

        assertThrows(NullPointerException.class, () -> validCurrencyCodeValidator.isValid(value, any()));
    }
}