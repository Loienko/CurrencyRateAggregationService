package net.ukr.dreamsicle.dto.bank;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BankCreateDTO {

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String bankName;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String state;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String city;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String street;
}
