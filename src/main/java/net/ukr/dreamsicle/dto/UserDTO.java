package net.ukr.dreamsicle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@ApiModel(description = "Class representing a user data for work with the application.")
@AllArgsConstructor
public class UserDTO {

    @ApiModelProperty(notes = "Unique identifier of the user. No two users can have the same id.")
    private Long id;

    @ApiModelProperty(notes = "User name", example = "test", required = true)
    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for name")
    private String name;

    @ApiModelProperty(notes = "Unique username of the user", example = "unique", required = true)
    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String username;

    @ApiModelProperty(notes = "Unique email of the user", example = "test@ukr.net", required = true)
    @NotBlank(message = "Please fill the email")
    @Email(message = "Please input valid data for email")
    private String email;

    @ApiModelProperty(notes = "User role 'admin' or 'role'", example = "[\"admin\"]", required = true)
    private Set<String> role;
}
