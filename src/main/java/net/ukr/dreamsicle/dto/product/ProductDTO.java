package net.ukr.dreamsicle.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.bank.Bank;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private Set<String> products;
    private String description;
    private Bank bank;
}
