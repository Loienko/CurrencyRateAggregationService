package net.ukr.dreamsicle.dto.gbsr;

import lombok.Builder;
import lombok.Data;
import net.ukr.dreamsicle.validation.country.CountryValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.*;

@Data
@Builder
public class BankData {

    @NotBlank(message = FILL_NAME_OF_THE_BANK)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = VALID_DATA_FOR_BANK_NAME)
    private String bankName;

    @Pattern(regexp = BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;

    @Pattern(regexp = IBAN_REGEX, message = VALID_DATA_FOR_IBAN_OF_BANK)
    private String iban;

    @CountryValidator(message = VALID_DATA_FOR_COUNTRY_OF_BANK)
    private String countryName;

    @NotBlank(message = FILL_THE_CITY_FOR_BANK)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_CITY_OF_BANK)
    private String cityName;

    @NotBlank(message = FILL_THE_BANK_STREET)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_STREET_OF_BANK)
    private String streetInformation;
}
