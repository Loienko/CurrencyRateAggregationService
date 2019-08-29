package net.ukr.dreamsicle.model;

import lombok.Data;

@Data
public class ModelForOutData {
    private String code;
    private String currencyPurchase;
    private String bankCurrencyPurchase;
    private String saleOfCurrency;
    private String bankSaleOfCurrency;
}
