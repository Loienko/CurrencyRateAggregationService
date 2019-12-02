package net.ukr.dreamsicle.util.product;

import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.model.product.TypeProduct;
import org.bson.types.ObjectId;

public class ProductProvider {
    public static final ObjectId ID = new ObjectId("5dde29e7c24a7c3eecef4910");
    public static final String BANK_CODE = "123456";
    public static final String DESCRIPTION = "description";
    public static final TypeProduct TYPE_PRODUCT = TypeProduct.DEPOSITS;


    public static Product getProductProvider() {
        return Product.builder()
                .bankCode(BANK_CODE)
                .type(TYPE_PRODUCT)
                .description(DESCRIPTION)
                .build();
    }

    public static ProductDTO getProductDTOProvider() {
        return ProductDTO.builder()
                .id(ID.toHexString())
                .bankCode(BANK_CODE)
                .type(TYPE_PRODUCT.getName())
                .description(DESCRIPTION)
                .build();
    }
}
