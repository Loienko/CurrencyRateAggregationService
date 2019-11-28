package net.ukr.dreamsicle.dto.product;

import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.model.product.TypeProduct;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface ProductMapper {

    default ProductDTO toProductDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId().toHexString())
                .bankCode(product.getBankCode())
                .type(product.getType().getName())
                .description(product.getDescription())
                .build();
    }

    default Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .bankCode(productDTO.getBankCode())
                .type(TypeProduct.getEnumFromString(productDTO.getType()))
                .description(productDTO.getDescription())
                .build();
    }

    default Page<ProductDTO> toProductDTOs(Page<Product> productPage) {
        return productPage.map(this::toProductDto);
    }
}
