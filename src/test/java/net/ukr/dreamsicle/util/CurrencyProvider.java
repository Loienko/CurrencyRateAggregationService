package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.model.Currency;

public class CurrencyProvider {
    public static final long ID = 1;
    public static final String CURRENCIES = "/currencies";
    public static final String BANK_NAME = "PUMB";
    public static final String CURRENCY_CODE = "USD";
    public static final String PURCHASE_CURRENCY = "25.12";
    public static final String SALE_OF_CURRENCY = "25.30";

    public static Currency getCurrencyProvider() {
        return Currency.builder()
                .id(ID)
                .bankName(BANK_NAME)
                .currencyCode(CURRENCY_CODE)
                .purchaseCurrency(PURCHASE_CURRENCY)
                .saleOfCurrency(SALE_OF_CURRENCY)
                .build();
    }

    public static CurrencyDTO getCurrencyDtoProvider() {
        return CurrencyDTO.builder()
                .id(ID)
                .bankName(BANK_NAME)
                .currencyCode(CURRENCY_CODE)
                .purchaseCurrency(PURCHASE_CURRENCY)
                .saleOfCurrency(SALE_OF_CURRENCY)
                .build();
    }

    public static Currency getCurrencyNotValidDataProvider(String bankName, String currencyCode, String purchaseCurrency, String saleOfCurrency) {
        return Currency.builder()
                .id(ID)
                .bankName(bankName)
                .currencyCode(currencyCode)
                .purchaseCurrency(purchaseCurrency)
                .saleOfCurrency(saleOfCurrency)
                .build();
    }
}
