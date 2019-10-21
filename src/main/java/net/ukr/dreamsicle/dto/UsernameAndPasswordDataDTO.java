package net.ukr.dreamsicle.dto;

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
@NoArgsConstructor
public class UsernameAndPasswordDataDTO {

    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String username;

    @NotBlank(message = "Please fill the password")
    @ValidPassword(message = "Please input valid data for password")
    private String password;
}
