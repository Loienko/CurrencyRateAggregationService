package net.ukr.dreamsicle.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ValidCurrencyCode interface for class {@link ValidCurrencyCodeValidator}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidCurrencyCodeValidator.class)
@Documented
public @interface ValidCurrencyCode {
    String message() default "wrong iso code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
