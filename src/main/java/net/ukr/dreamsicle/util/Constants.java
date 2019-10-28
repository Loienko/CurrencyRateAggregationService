package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.Application;

/**
 * Constants field for the project {@link Application}
 *
 * @author yurii.loienko
 * @version 1.0
 */
public class Constants {

    private Constants() {
    }

    //service layer
    public static final String BEARER = "Bearer ";
    public static final String PASSWORD = "Password1";
    public static final String CAUTION = "\nPlease set your own password!!!";
    public static final String SUCCESSFULLY_COMPLETED = "Successfully completed!";
    public static final String EMAIL_IS_ALREADY_IN_USE = "Email is already in use!";
    public static final String USERNAME_IS_ALREADY_IN_USE = "Username is already in use!";

    //security
    public static final String USER_NOT_FOUND_WITH_USERNAME_OR_EMAIL = "User Not Found with -> username or email : ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String JWT_TOKEN_IS_EXPIRED_OR_INVALID = "JWT token is expired or invalid";
    public static final String CAN_NOT_SET_USER_AUTHENTICATION_MESSAGE = "Can NOT set user authentication -> Message: {}";

    //exception
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String DATA_IS_STALE_PLEASE_RETRY = "Data is Stale. Please Retry";

    //user and userDetails
    public static final String ROLE_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS = "Role does not exist. Please choose the next items: ";

    public static final String FILL_THE_USERNAME = "Please fill the username";
    public static final String FILL_THE_SURNAME = "Please fill the surname";
    public static final String FILL_THE_EMAIL = "Please fill the email";
    public static final String FILL_THE_PASSWORD = "Please fill the password";
    public static final String FILL_THE_DESCRIPTION = "Please fill the description";

    public static final String INPUT_VALID_DATA_FOR_NAME = "Please input valid data for name";
    public static final String INPUT_VALID_DATA_FOR_USERNAME = "Please input valid data for username";
    public static final String INPUT_VALID_DATA_FOR_SURNAME = "Please input valid data for surname";
    public static final String INPUT_VALID_DATA_FOR_EMAIL = "Please input valid data for email";
    public static final String INPUT_VALID_DATA_FOR_PASSWORD = "Please input valid data for password";
    public static final String INPUT_VALID_DATA_FOR_PHONE = "Please input valid data for phone";
    public static final String INPUT_VALID_DATA_FOR_DESCRIPTION = "Please input valid data for description";

    //currency
    public static final String FILL_THE_BANK_NAME = "Please fill the bank name";
    public static final String FILL_THE_CURRENCY_CODE = "Please fill the currency code";
    public static final String FILL_THE_PURCHASE_CURRENCY = "Please fill the purchase currency";
    public static final String FILL_THE_SALE_OF_CURRENCY = "Please fill the sale of currency";

    public static final String INPUT_VALID_DATA_FOR_BANK_NAME = "Please input valid data for bank name";
    public static final String INPUT_VALID_DATA_FOR_CURRENCY_CODE = "Please input valid data for currency code";
    public static final String INPUT_VALID_DATA_FOR_PURCHASE_CURRENCY = "Please input valid data for purchase currency";
    public static final String INPUT_VALID_DATA_FOR_SALE_OF_CURRENCY = "Please input valid data for sale of currency";
}
