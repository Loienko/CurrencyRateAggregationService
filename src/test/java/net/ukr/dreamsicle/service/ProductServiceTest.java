package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.dto.product.ProductMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.repository.ProductRepository;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static net.ukr.dreamsicle.util.atm.AtmProvider.ID;
import static net.ukr.dreamsicle.util.bank.BankProvider.getBankProvider;
import static net.ukr.dreamsicle.util.bank.BankProvider.getBankProviderWithIdForAtmAndOfficeAndProduct;
import static net.ukr.dreamsicle.util.product.ProductProvider.getProductDTOProvider;
import static net.ukr.dreamsicle.util.product.ProductProvider.getProductProvider;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private Page<ProductDTO> productDTOPage;
    @Mock
    private Page<Product> productPage;
    @Mock
    private Pageable pageable;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(productMapper.toProductDTOs(productPage)).thenReturn(productDTOPage);

        Page<ProductDTO> actualProduct = productService.getAll(pageable);

        verify(productRepository).findAll(pageable);
        assertNotNull(actualProduct);
        assertEquals(productDTOPage, actualProduct);
    }

    @Test
    void findById() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(productMapper.toProductDto(product)).thenReturn(productDTO);

        ProductDTO actualProduct = productService.findById(ID);

        assertEquals(productDTO, actualProduct);
        assertNotNull(actualProduct);
        assertEquals(productDTO.getId(), actualProduct.getId());
        assertEquals(productDTO.getBankCode(), actualProduct.getBankCode());
        assertEquals(productDTO.getType(), actualProduct.getType());
        assertEquals(productDTO.getDescription(), actualProduct.getDescription());
    }

    @Test
    void testFindUserByIdIsNotPresentInDb() {
        when(productRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findById(ID));
    }

    @Test
    void create() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(bankRepository.save(bank)).thenReturn(bank);
        when(productMapper.toProductDto(product)).thenReturn(productDTO);

        ProductDTO actualProduct = productService.create(ID, productDTO);

        assertNotNull(actualProduct);
        verify(productRepository).save(product);
        assertEquals(productDTO.getId(), actualProduct.getId());
        assertEquals(productDTO.getBankCode(), actualProduct.getBankCode());
        assertEquals(productDTO.getType(), actualProduct.getType());
        assertEquals(productDTO.getDescription(), actualProduct.getDescription());
    }

    @Test
    void createProductBankIsNotPresentInDb() {
        ProductDTO productDTO = getProductDTOProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.create(ID, productDTO));
    }

    @Test
    void createProductSaveToBankReturnedTransactionException() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(bankRepository.save(bank)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> productService.create(ID, productDTO));
    }

    @Test
    void createProductReturnedTransactionException() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> productService.create(ID, productDTO));
    }

    @Test
    void updateProduct() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        Bank bank = getBankProviderWithIdForAtmAndOfficeAndProduct();
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(bankRepository.findBankByBankCode(bank.getBankCode())).thenReturn(Optional.of(bank));
        when(bankRepository.save(bank)).thenReturn(bank);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductDto(product)).thenReturn(productDTO);

        ProductDTO actualProduct = productService.update(ID, productDTO);

        assertNotNull(actualProduct);
        verify(productRepository).save(product);
        assertEquals(productDTO.getId(), actualProduct.getId());
        assertEquals(productDTO.getBankCode(), actualProduct.getBankCode());
        assertEquals(productDTO.getType(), actualProduct.getType());
        assertEquals(productDTO.getDescription(), actualProduct.getDescription());
    }

    @Test
    void updateProductIsNotPresentInDb() {
        when(productRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.create(ID, getProductDTOProvider()));
    }

    @Test
    void updateProductBankIsNotPresentInDb() {
        Product product = getProductProvider();
        ProductDTO productDTO = getProductDTOProvider();
        when(productRepository.findById(ID)).thenReturn(Optional.of(product));
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(bankRepository.findBankByBankCode(product.getBankCode())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.create(ID, productDTO));
    }
}
