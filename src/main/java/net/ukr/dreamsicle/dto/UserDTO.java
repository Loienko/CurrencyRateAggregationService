package net.ukr.dreamsicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for name")
    private String name;

    @NotBlank(message = "Please fill the username")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String username;

    @NotBlank(message = "Please fill the correct email")
    @Email(message = "Please input valid data for email")
    private String email;

    private Set<String> role;

    @NotBlank(message = "Please fill the password")
    @Size(min = 6, max = 40, message = "Please fill the correct password from 6 to 40 letters")
    private String password;
}
