package net.ukr.dreamsicle.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String bankName;
    private String currencyCode;
    private String purchaseCurrency;
    private String saleOfCurrency;

    public Currency() {
    }

    public Currency(String bankName, String currencyCode, String purchaseCurrency, String saleOfCurrency) {
        this.bankName = bankName;
        this.currencyCode = currencyCode;
        this.purchaseCurrency = purchaseCurrency;
        this.saleOfCurrency = saleOfCurrency;
    }

    public Currency(int id, String bankName, String currencyCode, String purchaseCurrency, String saleOfCurrency) {
        this.id = id;
        this.bankName = bankName;
        this.currencyCode = currencyCode;
        this.purchaseCurrency = purchaseCurrency;
        this.saleOfCurrency = saleOfCurrency;
    }
}
