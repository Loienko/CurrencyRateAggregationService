package net.ukr.dreamsicle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@ApiModel(description = "A class representing the username and password for accessing the program functionality.")
@NoArgsConstructor
public class UsernameAndPasswordDataDTO {

    @ApiModelProperty(notes = "User name", example = "admin", required = true)
    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String username;

    @ApiModelProperty(notes = "Conditional word or set of signs intended to confirm identity or authority.", example = "Qwerty1", required = true)
    @NotBlank(message = "Please fill the password")
    @ValidPassword(message = "Please input valid data for password")
    private String password;
}
