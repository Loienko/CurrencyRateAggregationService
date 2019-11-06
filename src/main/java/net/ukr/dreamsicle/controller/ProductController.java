package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @GetMapping
    public Page<ProductDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return productService.getAll(page);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable @Min(1) @Positive long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProductDTO create(@Validated @RequestBody ProductDTO productDTO) {
        return productService.createBank(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ProductDTO update(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody ProductDTO productDTO) {
        return productService.updateBank(id, productDTO);
    }
}
