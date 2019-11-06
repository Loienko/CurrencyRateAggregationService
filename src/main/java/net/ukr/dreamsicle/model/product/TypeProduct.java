package net.ukr.dreamsicle.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

    @Getter
    private String name;
}
