package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    @NotNull
    @Size(min = 1, message="Bank name should have at least 1 characters")
    private String bankName;

    @NotNull
    @Size(min = 1, message="Currency Code should have at least 1 characters")
    private String currencyCode;

    @NotNull
    @Size(min = 1, message="Purchase Currency should have at least 1 characters - '00.00'")
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private String purchaseCurrency;

    @NotNull
    @Size(min = 1, message="Sale Of Currency should have at least 1 characters - '00.00'")
    private String saleOfCurrency;
}
