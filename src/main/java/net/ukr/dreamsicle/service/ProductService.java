package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class ProductService {
    public Page<ProductDTO> getAll(Pageable page) {
        return null;
    }

    public ProductDTO findById(long id) {
        return null;
    }

    public ProductDTO createBank(ProductDTO productDTO) {
        return null;
    }

    public ProductDTO updateBank(long id, ProductDTO productDTO) {
        return null;
    }
}
