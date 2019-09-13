package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    @NotBlank(message = "Please fill the bank name")
    private String bankName;

    @NotBlank(message = "Please fill the currency code")
    private String currencyCode;

    @NotBlank(message = "Please fill the purchase currency")
    private String purchaseCurrency;

    @NotBlank(message = "Please fill the sale of currency")
    private String saleOfCurrency;
}
