package net.ukr.dreamsicle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.ValidCurrencyCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@ApiModel(description = "A class representing the exchange rate for each bankю")
@AllArgsConstructor
public class CurrencyDTO {

    @ApiModelProperty(notes = "Unique identifier of the currency. No two currency can have the same id.", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Name of used bank", example = "Pumb")
    @NotBlank(message = "Please fill the bank name")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for bank name")
    private String bankName;

    @ApiModelProperty(notes = "Unique identifier of the currency. Use ISO 4217 CURRENCY CODES for the presentation of currencies", example = "USD")
    @NotBlank(message = "Please fill the currency code")
    @ValidCurrencyCode(message = "Please input valid data for currency code")
    private String currencyCode;

    @ApiModelProperty(notes = "Currency purchase price", example = "27.85")
    @NotBlank(message = "Please fill the purchase currency")
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,5}$", message = "Please input valid data for purchase currency")
    private String purchaseCurrency;

    @ApiModelProperty(notes = "Currency sale value", example = "28.10")
    @NotBlank(message = "Please fill the sale of currency")
    @Pattern(regexp = "^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*\\.[0-9]{1,6}$", message = "Please input valid data for sale of currency")
    private String saleOfCurrency;
}
