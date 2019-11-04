package net.ukr.dreamsicle.dto.userDetails;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.ukr.dreamsicle.util.ConstantsRegex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static net.ukr.dreamsicle.util.Constants.*;

/**
 * DTO class for input user details data
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@ApiModel(description = "A class representing additional data for the user.")
@AllArgsConstructor
public class UserDetailsDTO {

    @ApiModelProperty(notes = "User surname", example = "Surname", required = true)
    @NotBlank(message = FILL_THE_SURNAME)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_SURNAME)
    private String surname;

    @ApiModelProperty(notes = "User phone number", example = "+38(012)345-67-89")
    @Pattern(regexp = ConstantsRegex.PHONE_REGEX, message = INPUT_VALID_DATA_FOR_PHONE)
    private String phone;

    @ApiModelProperty(notes = "User description", example = "Good Person!", required = true)
    @Size(max = 256)
    @NotBlank(message = FILL_THE_DESCRIPTION)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_DESCRIPTION)
    private String description;
}
