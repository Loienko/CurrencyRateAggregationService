package net.ukr.dreamsicle.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.util.Constants;
import net.ukr.dreamsicle.validation.country.CountryValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.CITY_AND_STREET_REGEX;
import static net.ukr.dreamsicle.util.ConstantsRegex.INPUT_STRING_VALUE_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankUpdateDTO {

    private String id;

    @NotBlank(message = FILL_NAME_OF_THE_BANK)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = VALID_DATA_FOR_BANK_NAME)
    private String bankName;

    @CountryValidator(message = VALID_DATA_FOR_COUNTRY_OF_BANK)
    private String state;

    @NotBlank(message = Constants.FILL_THE_CITY_FOR_BANK)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_CITY_OF_BANK)
    private String city;

    @NotBlank(message = FILL_THE_BANK_STREET)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_STREET_OF_BANK)
    private String street;
}
