package net.ukr.dreamsicle.dto.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.work.WorkTimes;
import net.ukr.dreamsicle.validation.country.CountryValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO {

    private String id;

    @Pattern(regexp = BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;

    @NotBlank(message = FILL_NAME_OF_THE_OFFICE)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = VALID_DATA_FOR_OFFICE_NAME)
    private String name;

    @NotBlank(message = FILL_THE_CITY_FOR_OFFICE)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_CITY_OF_OFFICE)
    private String city;

    @CountryValidator(message = VALID_DATA_FOR_COUNTRY_OF_OFFICE)
    private String state;

    @NotBlank(message = FILL_THE_OFFICE_STREET)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_STREET_OF_OFFICE)
    private String street;

    @Valid
    private Set<WorkTimes> workTimes;
}
