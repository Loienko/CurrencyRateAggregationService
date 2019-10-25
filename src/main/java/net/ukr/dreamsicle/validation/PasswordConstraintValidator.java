package net.ukr.dreamsicle.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * PasswordConstraintValidator class that implements {@link ConstraintValidator} for class {@link ValidPassword}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    /**
     * Validates the supplied password data against the rules in this validator
     *
     * @param password
     * @param context
     * @return Boolean value of the verified password data
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(6, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new WhitespaceRule()));

        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.buildConstraintViolationWithTemplate(validator.getMessages(result).iterator().next()).addConstraintViolation().disableDefaultConstraintViolation();
        return false;
    }
}