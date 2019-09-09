package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO {
    @NotNull
    @NotBlank(message = "Please fill the bank name")
    @Length(max = 1024, message = "Message too long (more than 1kB)")
    @Size(min = 1, message="Bank name should have at least 1 characters")
    private String bankName;

    @NotNull
    @NotBlank(message = "Please fill the currency code")
    @Length(max = 1024, message = "Message too long (more than 1кB)")
    @Size(min = 1, message="Currency Code should have at least 1 characters")
    private String currencyCode;

    @NotNull
    @NotBlank(message = "Please fill the purchase currency")
    @Length(max = 1024, message = "Message too long (more than 1кB)")
    @Size(min = 1, message="Purchase Currency should have at least 1 characters")
    @Digits(integer = Integer.MAX_VALUE, fraction = 3)
    private String purchaseCurrency;

    @NotNull
    @NotBlank(message = "Please fill the sale of currency")
    @Length(max = 1024, message = "Message too long (more than 1кB)")
    @Size(min = 1, message="Sale Of Currency should have at least 1 characters")
    private String saleOfCurrency;
}
