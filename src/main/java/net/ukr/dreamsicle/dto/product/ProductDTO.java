package net.ukr.dreamsicle.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static net.ukr.dreamsicle.util.Constants.*;
import static net.ukr.dreamsicle.util.ConstantsRegex.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;

    @Pattern(regexp = BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;
    private String type;

    @Size(max = 256)
    @Pattern(regexp = DESCRIPTION_REGEX, message = INPUT_VALID_DATA_FOR_DESCRIPTION)
    private String description;
}
