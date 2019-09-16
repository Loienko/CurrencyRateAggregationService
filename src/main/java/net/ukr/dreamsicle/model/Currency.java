package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String bankName;
    private String currencyCode;
    private String purchaseCurrency;
    private String saleOfCurrency;
    private int version;

    public Currency(String bankName, String currencyCode, String purchaseCurrency, String saleOfCurrency) {
        this.bankName = bankName;
        this.currencyCode = currencyCode;
        this.purchaseCurrency = purchaseCurrency;
        this.saleOfCurrency = saleOfCurrency;
    }
}
