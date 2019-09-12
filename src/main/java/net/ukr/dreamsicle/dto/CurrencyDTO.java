package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.ValidCurrencyCode;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    @NotNull
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$")
    private String bankName;

    @NotNull
    @ValidCurrencyCode
    private String currencyCode;

    @NotNull
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$")
    private String purchaseCurrency;

    @NotNull
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$")
    private String saleOfCurrency;
}
