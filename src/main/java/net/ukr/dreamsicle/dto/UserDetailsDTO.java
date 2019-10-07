package net.ukr.dreamsicle.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDetailsDTO {

    @NotBlank(message = "Please fill the surname")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for surname")
    private String surname;

    @Pattern(regexp = "^\\+?([0-9]{2})?\\(?[0-9]{3}\\)?[0-9]{3}\\-?[0-9]{2}\\-?[0-9]{2}$", message = "Please input valid data for phone")
    private long phone;

    @Size(max = 256)
    @NotBlank(message = "Please fill the description")
    @Pattern(regexp = "^[a-zA-Zа-яёА-ЯЁ\\s\\-]+$", message = "Please input valid data for username")
    private String description;
}