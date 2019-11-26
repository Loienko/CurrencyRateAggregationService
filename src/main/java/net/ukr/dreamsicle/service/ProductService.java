package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.dto.product.ProductMapper;
import net.ukr.dreamsicle.exception.CollectionNotFoundException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.repository.ProductRepository;
import net.ukr.dreamsicle.util.Constants;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BankRepository bankRepository;

    public Page<ProductDTO> getAll(Pageable pageable) {
        return productMapper.toProductDTOs(productRepository.findAll(pageable));
    }

    public ProductDTO findById(ObjectId id) {
        return productRepository
                .findById(id)
                .map(productMapper::toProductDto)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public ProductDTO create(String bankCode, ProductDTO productDTO) {
        Bank bank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);
        Product actualProduct = saveProduct(bank.getBankCode(), productMapper.toProduct(productDTO));
        checkProductsFromBankNotNull(bank.getProducts()).add(actualProduct);
        bankRepository.save(bank);

        return productMapper.toProductDto(actualProduct);
    }

    private List<Product> checkProductsFromBankNotNull(List<Product> products) {
        return Optional.ofNullable(products).orElseThrow(() -> new CollectionNotFoundException(Constants.PRODUCTS_FROM_BANK_DATA_NOT_FOUND));
    }

    private Product saveProduct(String bankCode, Product product) {
        return productRepository.save(Product.builder()
                .bankCode(bankCode)
                .type(product.getType())
                .description(product.getDescription())
                .build());
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public ProductDTO update(ObjectId id, ProductDTO productDTO) {
        Product productById = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        Product actualProduct = productMapper.toProduct(productDTO);
        actualProduct.setId(id);
        actualProduct.setBankCode(productById.getBankCode());

        Bank bank = bankRepository.findBankByBankCode(productById.getBankCode()).orElseThrow(ResourceNotFoundException::new);
        checkProductsFromBankNotNull(bank.getProducts()).stream()
                .filter(product -> product.getId().equals(id))
                .forEach(product -> {
                    product.setBankCode(actualProduct.getBankCode());
                    product.setType(actualProduct.getType());
                    product.setDescription(actualProduct.getDescription());
                });
        bankRepository.save(bank);

        return productMapper.toProductDto(productRepository.save(actualProduct));
    }

    public List<Product> createProduct(String bankCode, List<Product> listProduct) {
        return listProduct.stream()
                .map(product -> saveProduct(bankCode, product))
                .collect(Collectors.toList());
    }
}
