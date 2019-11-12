package net.ukr.dreamsicle.dto.atm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.work.WorkTimes;
import net.ukr.dreamsicle.util.ConstantsRegex;
import net.ukr.dreamsicle.validation.country.CountryValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.INPUT_STRING_VALUE_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmDTO {

    private String id;

    @Pattern(regexp = ConstantsRegex.BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;

    @NotBlank(message = FILL_NAME_OF_THE_ATM)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = VALID_DATA_FOR_ATM_NAME)
    private String name;

    @NotBlank(message = FILL_THE_CITY_FOR_ATM)
    @Pattern(regexp = ConstantsRegex.CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_CITY_OF_ATM)
    private String city;

    @CountryValidator(message = VALID_DATA_FOR_COUNTRY_OF_ATM)
    private String state;

    @NotBlank(message = FILL_THE_ATM_STREET)
    @Pattern(regexp = ConstantsRegex.CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_STREET_OF_ATM)
    private String street;

    @Valid
    private Set<WorkTimes> workTimes;
}
