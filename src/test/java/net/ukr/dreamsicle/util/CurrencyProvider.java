package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.model.Currency;

public class CurrencyProvider {
    private static final String BANK_NAME = "PUMB";
    private static final String CURRENCY_CODE = "USD";
    private static final String PURCHASE_CURRENCY = "25.12";
    private static final String SALE_OF_CURRENCY = "25.30";

    public static Currency getCurrencyProvider(int index) {
        return new Currency(index, BANK_NAME, CURRENCY_CODE, PURCHASE_CURRENCY, SALE_OF_CURRENCY);
    }
}
