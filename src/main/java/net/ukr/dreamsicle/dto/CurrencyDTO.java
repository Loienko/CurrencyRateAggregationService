package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.ValidCurrencyCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {

    private int id;

    @NotBlank(message = "Please fill the bank name")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for bank name")
    private String bankName;

    @NotBlank(message = "Please fill the currency code")
    @ValidCurrencyCode(message = "Please input valid data for currency code")
    private String currencyCode;

    @NotBlank(message = "Please fill the purchase currency")
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$", message = "Please input valid data for purchase currency")
    private String purchaseCurrency;

    @NotBlank(message = "Please fill the sale of currency")
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$", message = "Please input valid data for sale of currency")
    private String saleOfCurrency;
}
