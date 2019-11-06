package net.ukr.dreamsicle.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.product.TypeProduct;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String id;
    private String bankCode;
    private TypeProduct type;
    private String description;
}
