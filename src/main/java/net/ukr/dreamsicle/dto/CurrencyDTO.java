package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.ValidCurrencyCode;

<<<<<<< HEAD
import javax.validation.constraints.*;
=======
import javax.validation.constraints.NotBlank;
>>>>>>> origin/dev

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
<<<<<<< HEAD
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
=======
    @NotBlank(message = "Please fill the bank name")
    private String bankName;

    @NotBlank(message = "Please fill the currency code")
    private String currencyCode;

    @NotBlank(message = "Please fill the purchase currency")
    private String purchaseCurrency;

    @NotBlank(message = "Please fill the sale of currency")
>>>>>>> origin/dev
    private String saleOfCurrency;
}
