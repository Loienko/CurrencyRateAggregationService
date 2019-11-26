package net.ukr.dreamsicle.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.product.TypeProduct;
import org.bson.types.ObjectId;

import javax.validation.constraints.Pattern;

import static net.ukr.dreamsicle.util.Constants.SHOULD_NOT_EXCEED_256_CHARACTERS;
import static net.ukr.dreamsicle.util.Constants.VALID_DATA_FOR_BANK_CODE;
import static net.ukr.dreamsicle.util.ConstantsRegex.BANK_CODE_REGEX;
import static net.ukr.dreamsicle.util.ConstantsRegex.INPUT_STRING_VALUE_REGEX;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private ObjectId id;

    @Pattern(regexp = BANK_CODE_REGEX, message = VALID_DATA_FOR_BANK_CODE)
    private String bankCode;
    private TypeProduct type;

    @Pattern(regexp = INPUT_STRING_VALUE_REGEX, message = SHOULD_NOT_EXCEED_256_CHARACTERS)
    private String description;
}
