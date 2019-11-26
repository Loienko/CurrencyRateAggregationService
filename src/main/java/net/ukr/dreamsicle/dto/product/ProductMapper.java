package net.ukr.dreamsicle.dto.product;

import net.ukr.dreamsicle.model.product.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface ProductMapper {

    ProductDTO toProductDto(Product product);

    Product toProduct(ProductDTO productDTO);

    default Page<ProductDTO> toProductDTOs(Page<Product> productPage) {
        return productPage.map(this::toProductDto);
    }
}
