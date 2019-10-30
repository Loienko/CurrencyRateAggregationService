package net.ukr.dreamsicle.dto.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.validation.currencyCode.ValidCurrencyCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeDTO {
    private Long id;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String city;

    @NotBlank(message = "null")
    @ValidCurrencyCode(message = "null")
    private String state;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String street;

    private Set<String> workTimes;
}
