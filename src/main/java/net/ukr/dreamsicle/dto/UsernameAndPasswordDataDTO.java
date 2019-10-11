package net.ukr.dreamsicle.dto;

import lombok.*;
import net.ukr.dreamsicle.validation.ValidPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
@NoArgsConstructor
public class UsernameAndPasswordDataDTO {

    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String username;

    @NotBlank(message = "Please fill the password")
    @ValidPassword(message = "Please input valid data for password")
    private String password;
}
