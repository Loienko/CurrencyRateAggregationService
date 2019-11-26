package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@AllArgsConstructor
@RequestMapping
public class ProductController {

    private ProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return productService.getAll(page);
    }

    @GetMapping("/products/{id}")
    public ProductDTO findById(@PathVariable @Min(1) @Positive ObjectId id) {
        return productService.findById(id);
    }

    @PostMapping("/banks/{bankCode}/products")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO create(@PathVariable @Min(1) @Positive String bankCode, @Validated @RequestBody ProductDTO productDTO) {
        return productService.create(bankCode, productDTO);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ProductDTO update(@PathVariable @Min(1) @Positive ObjectId id, @Validated @RequestBody ProductDTO productDTO) {
        return productService.update(id, productDTO);
    }
}
