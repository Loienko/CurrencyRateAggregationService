package net.ukr.dreamsicle.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.model.partners.Partner;
import net.ukr.dreamsicle.validation.country.CountryValidator;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    private String id;

    @NotBlank(message = FILL_NAME_OF_THE_BANK)
    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = VALID_DATA_FOR_BANK_NAME)
    private String bankName;

    @Pattern(regexp = BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;

    @Pattern(regexp = IBAN_REGEX, message = VALID_DATA_FOR_IBAN_OF_BANK)
    private String iban;

    @CountryValidator(message = VALID_DATA_FOR_COUNTRY_OF_BANK)
    private String state;

    @NotBlank(message = FILL_THE_CITY_FOR_BANK)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_CITY_OF_BANK)
    private String city;

    @NotBlank(message = FILL_THE_BANK_STREET)
    @Pattern(regexp = CITY_AND_STREET_REGEX, message = VALID_DATA_FOR_STREET_OF_BANK)
    private String street;

    @Valid
    private List<Partner> partners;
    @Valid
    private List<ProductDTO> products;
    @Valid
    private List<OfficeDTO> offices;
    @Valid
    private List<AtmDTO> atms;
}
