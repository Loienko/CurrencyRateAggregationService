package net.ukr.dreamsicle.dto.currency;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.ukr.dreamsicle.util.ConstantsRegex;
import net.ukr.dreamsicle.validation.currencyCode.ValidCurrencyCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static net.ukr.dreamsicle.util.Constants.*;

/**
 * DTO class for input currency data
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@ApiModel(description = "A class representing the exchange rate for each bankю")
@AllArgsConstructor
public class CurrencyDTO {

    @ApiModelProperty(notes = "Unique identifier of the currency. No two currency can have the same id.", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Name of used bank", example = "Pumb")
    @NotBlank(message = FILL_THE_BANK_NAME)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_BANK_NAME)
    private String bankName;

    @ApiModelProperty(notes = "Unique identifier of the currency. Use ISO 4217 CURRENCY CODES for the presentation of currencies", example = "USD")
    @NotBlank(message = FILL_THE_CURRENCY_CODE)
    @ValidCurrencyCode(message = INPUT_VALID_DATA_FOR_CURRENCY_CODE)
    private String currencyCode;

    @ApiModelProperty(notes = "Currency purchase price", example = "27.85")
    @NotBlank(message = FILL_THE_PURCHASE_CURRENCY)
    @Pattern(regexp = ConstantsRegex.PURCHASE_CURRENCY_REGEX, message = INPUT_VALID_DATA_FOR_PURCHASE_CURRENCY)
    private String purchaseCurrency;

    @ApiModelProperty(notes = "Currency sale value", example = "28.10")
    @NotBlank(message = FILL_THE_SALE_OF_CURRENCY)
    @Pattern(regexp = ConstantsRegex.SALE_OF_CURRENCY_REGEX, message = INPUT_VALID_DATA_FOR_SALE_OF_CURRENCY)
    private String saleOfCurrency;
}
