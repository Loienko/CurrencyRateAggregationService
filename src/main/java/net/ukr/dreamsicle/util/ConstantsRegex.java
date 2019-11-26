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

    public static final String INPUT_WORK_TIMES_REGEX = "((1[0-2]|0?[0-9]):([0-5][0-9]) ?([AaPp][Mm]))";
    public static final String INPUT_STRING_VALUE_REGEX = "^[a-zA-Z]+(([',.\\-][a-zA-Z ])?[a-zA-Z]*)*$";
    public static final String SALE_OF_CURRENCY_REGEX = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,6}$";
    public static final String PURCHASE_CURRENCY_REGEX = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$";
    public static final String PHONE_REGEX = "^\\+?([0-9]{2})?\\(?[0-9]{3}\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$";

    public static final String BANK_CODE_REGEX = "\\d{6}";
    public static final String CITY_AND_STREET_REGEX = "([a-zA-Z]+|[a-zA-Z]+\\\\s[a-zA-Z]+)";
    public static final String IBAN_REGEX = "(\\d\\s){29}";
    public static final String DESCRIPTION_REGEX = "^[\\w_\\-' ]+$";
}
