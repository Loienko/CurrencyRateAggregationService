package net.ukr.dreamsicle.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum TypeProduct {
    CREDIT_CARD("Credit card"),
    LENDING("Lending"),
    DEPOSITS("Deposits"),
    MONEY_TRANSFERS("Money transfers"),
    PAYMENT_ACCEPTANCE("Payment acceptance"),
    TRAVELERS_CHECKS("Travelers checks"),
    RENTAL_OF_INDIVIDUAL_SAFES("Rental of individual safes"),
    INVESTMENTS("Investments"),
    FACTORING("Factoring"),
    COLLECTION("Collection"),
    CASH_COLLECTION("Cash collection"),
    CUSTODIAN_SERVICES("Custodian services");

    public static final String PRODUCT_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS = "Product does not exist. Please choose the next items:";
    @Getter
    private String name;

    public static <T extends Enum<?>> TypeProduct getEnumFromString(String stringValue) {
        if (stringValue != null) {
            try {
                return Enum.valueOf(TypeProduct.class, stringValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(PRODUCT_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS + Arrays.toString(TypeProduct.values()));
            }
        }
        return null;
    }
}
