package net.ukr.dreamsicle.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.ukr.dreamsicle.util.Constants;
import net.ukr.dreamsicle.util.ConstantsRegex;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

import static net.ukr.dreamsicle.util.Constants.*;

/**
 * DTO class for input user data
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@NoArgsConstructor
@Builder
@ApiModel(description = "Class representing a user data for work with the application.")
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(notes = "Unique identifier of the user. No two users can have the same id.")
    private Long id;

    @ApiModelProperty(notes = "User name", example = "test", required = true)
    @NotBlank(message = FILL_THE_USERNAME)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_NAME)
    private String name;

    @ApiModelProperty(notes = "Unique username of the user", example = "unique", required = true)
    @NotBlank(message = Constants.FILL_THE_USERNAME)
    @Pattern(regexp = ConstantsRegex.INPUT_STRING_VALUE_REGEX, message = INPUT_VALID_DATA_FOR_USERNAME)
    private String username;

    @ApiModelProperty(notes = "Unique email of the user", example = "test@ukr.net", required = true)
    @NotBlank(message = FILL_THE_EMAIL)
    @Email(message = INPUT_VALID_DATA_FOR_EMAIL)
    private String email;

    @ApiModelProperty(notes = "User role 'admin' or 'role'", example = "[\"admin\"]", required = true)
    private Set<String> role;
}
