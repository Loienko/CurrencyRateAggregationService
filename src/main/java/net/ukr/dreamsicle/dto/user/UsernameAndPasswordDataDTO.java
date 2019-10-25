package net.ukr.dreamsicle.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.ukr.dreamsicle.util.ConstantsRegex;
import net.ukr.dreamsicle.validation.password.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static net.ukr.dreamsicle.util.Constants.*;

/**
 * DTO class for user request by username and password
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@ApiModel(description = "A class representing the username and password for accessing the program functionality.")
@NoArgsConstructor
public class UsernameAndPasswordDataDTO {

    @ApiModelProperty(notes = "User name", example = "admin", required = true)
    @NotBlank(message = FILL_THE_USERNAME)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_USERNAME)
    private String username;

    @ApiModelProperty(notes = "Conditional word or set of signs intended to confirm identity or authority.", example = "Qwerty1", required = true)
    @NotBlank(message = FILL_THE_PASSWORD)
    @ValidPassword(message = INPUT_VALID_DATA_FOR_PASSWORD)
    private String password;
}
