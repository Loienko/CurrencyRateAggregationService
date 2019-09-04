package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    private String bankName;
    private String currencyCode;
    private String purchaseCurrency;
    private String saleOfCurrency;
}
