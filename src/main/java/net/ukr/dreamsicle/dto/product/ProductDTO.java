package net.ukr.dreamsicle.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;

public class ProductDTO {

    private Long id;

    private Set<String> products;

    @NotBlank(message = "null")
    @Pattern(regexp = "null", message = "null")
    private String description;
}
