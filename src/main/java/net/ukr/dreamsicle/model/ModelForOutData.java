package net.ukr.dreamsicle.model;

import lombok.Data;

@Data
public class ModelForOutData {
    private String code;
    private String currencyPurchase;
    private String bankCurrencyPurchase;
    private String saleOfCurrency;
    private String bankSaleOfCurrency;

    public ModelForOutData(String code, String currencyPurchase, String bankCurrencyPurchase, String saleOfCurrency, String bankSaleOfCurrency) {
        this.code = code;
        this.currencyPurchase = currencyPurchase;
        this.bankCurrencyPurchase = bankCurrencyPurchase;
        this.saleOfCurrency = saleOfCurrency;
        this.bankSaleOfCurrency = bankSaleOfCurrency;
    }

    public ModelForOutData() {
    }
}
