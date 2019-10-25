package net.ukr.dreamsicle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "Please fill the surname")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for surname")
    private String surname;

    @ApiModelProperty(notes = "User phone number", example = "+38(012)345-67-89")
    @Pattern(regexp = "^\\+?([0-9]{2})?\\(?[0-9]{3}\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$", message = "Please input valid data for phone")
    private String phone;

    @ApiModelProperty(notes = "User description", example = "Good Person!", required = true)
    @Size(max = 256)
    @NotBlank(message = "Please fill the description")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for description")
    private String description;
}