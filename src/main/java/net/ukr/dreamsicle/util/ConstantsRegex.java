package net.ukr.dreamsicle.util;

/**
 * The class for checking user input by regex
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class ConstantsRegex {

    private ConstantsRegex() {
    }

    public static final String INPUT_STRING_VALUE_REGEX = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$";
    public static final String SALE_OF_CURRENCY_REGEX = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,6}$";
    public static final String PURCHASE_CURRENCY_REGEX = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$";
    public static final String PHONE_REGEX = "^\\+?([0-9]{2})?\\(?[0-9]{3}\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$";
}
