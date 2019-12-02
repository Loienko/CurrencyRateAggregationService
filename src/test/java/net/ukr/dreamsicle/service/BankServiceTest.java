package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.dto.bank.BankUpdateDTO;
import net.ukr.dreamsicle.exception.CustomDataAlreadyExistsException;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.repository.AtmRepository;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.repository.OfficeRepository;
import net.ukr.dreamsicle.repository.ProductRepository;
import net.ukr.dreamsicle.util.atm.AtmProvider;
import net.ukr.dreamsicle.util.bank.BankProvider;
import net.ukr.dreamsicle.util.office.OfficeProvider;
import net.ukr.dreamsicle.util.product.ProductProvider;
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
import static net.ukr.dreamsicle.util.bank.BankProvider.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BankServiceTest {

    @InjectMocks
    private BankService bankService;
    @Mock
    private AtmService atmService;
    @Mock
    private OfficeService officeService;
    @Mock
    private ProductService productService;
    @Mock
    private BankMapper bankMapper;
    @Mock
    private Page<BankDTO> bankDTOPage;
    @Mock
    private Page<Bank> bankPage;
    @Mock
    private Pageable pageable;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private AtmRepository atmRepository;
    @Mock
    private OfficeRepository officeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        when(bankRepository.findAll(pageable)).thenReturn(bankPage);
        when(bankMapper.toBankDTOs(bankPage)).thenReturn(bankDTOPage);

        Page<BankDTO> actualBank = bankService.getAll(pageable);

        verify(bankRepository).findAll(pageable);
        assertNotNull(actualBank);
        assertEquals(bankDTOPage, actualBank);
    }

    @Test
    void testFindUserById() {
        Bank bank = getBankProvider();
        BankDTO bankDTO = getBankDtoProvider();
        when(bankRepository.findById(BankProvider.ID)).thenReturn(Optional.of(bank));
        when(bankMapper.toBankDto(bank)).thenReturn(bankDTO);

        BankDTO actualBank = bankService.findById(BankProvider.ID);

        assertEquals(bankDTO, actualBank);
        assertNotNull(actualBank);
        assertEquals(bankDTO.getId(), actualBank.getId());
        assertEquals(bankDTO.getBankCode(), actualBank.getBankCode());
        assertEquals(bankDTO.getIban(), actualBank.getIban());
        assertEquals(bankDTO.getBankName(), actualBank.getBankName());
        assertEquals(bankDTO.getState(), actualBank.getState());
        assertEquals(bankDTO.getCity(), actualBank.getCity());
        assertEquals(bankDTO.getStreet(), actualBank.getStreet());
        assertEquals(bankDTO.getProducts(), actualBank.getProducts());
        assertEquals(bankDTO.getOffices(), actualBank.getOffices());
        assertEquals(bankDTO.getAtms(), actualBank.getAtms());
    }

    @Test
    void testFindBankByIdIsNotPresentInDb() {
        when(bankRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.findById(BankProvider.ID));
    }

    @Test
    void testUpdateBank() {
        Bank bank = getBankProvider();
        BankDTO bankDTO = getBankDtoProvider();
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        when(bankRepository.findById(BankProvider.ID)).thenReturn(Optional.of(bank));
        when(bankRepository.existsBankByBankName(bankUpdateDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankMapper.toBank(bankUpdateDTO)).thenReturn(bank);
        when(bankRepository.save(bank)).thenReturn(bank);
        when(bankMapper.toBankDto(bank)).thenReturn(bankDTO);

        BankDTO actualBank = bankService.update(BankProvider.ID, bankUpdateDTO);

        verify(bankRepository).save(bank);
        assertNotNull(actualBank);
        assertEquals(bankUpdateDTO.getId(), actualBank.getId());
        assertEquals(bankUpdateDTO.getBankName(), actualBank.getBankName());
        assertEquals(bankUpdateDTO.getState(), actualBank.getState());
        assertEquals(bankUpdateDTO.getCity(), actualBank.getCity());
        assertEquals(bankUpdateDTO.getStreet(), actualBank.getStreet());
    }

    @Test
    void testUpdateBankIsBankNotUniqueUnDB() {
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        bankUpdateDTO.setBankName("PrivatBank");
        when(bankRepository.findById(BankProvider.ID)).thenReturn(Optional.of(getBankProvider()));
        when(bankRepository.existsBankByBankName(bankUpdateDTO.getBankName())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> bankService.update(BankProvider.ID, bankUpdateDTO));
    }

    @Test
    void testUpdateBankIsNotPresentInDb() {
        when(bankRepository.findById(BankProvider.ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bankService.update(BankProvider.ID, getBankUpdateDtoProvider()));
    }

    @Test
    void testUpdateBankReturnedTransactionException() {
        Bank bank = getBankProvider();
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        when(bankRepository.findById(BankProvider.ID)).thenReturn(Optional.of(bank));
        when(bankRepository.existsBankByBankName(bankUpdateDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankMapper.toBank(bankUpdateDTO)).thenReturn(bank);
        when(bankRepository.save(bank)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> bankService.update(BankProvider.ID, bankUpdateDTO));
    }

    @Test
    void createBank() {
        Bank bank = getBankProvider();
        BankDTO bankDTO = getBankDtoProvider();
        Product product = ProductProvider.getProductProvider();
        Office office = OfficeProvider.getOfficeProvider();
        ATM atm = AtmProvider.getAtmProvider();
        when(bankRepository.existsBankByBankName(bankDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByBankCode(bankDTO.getBankCode())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByIban(bankDTO.getIban())).thenReturn(Boolean.FALSE);
        when(bankMapper.toBank(bankDTO)).thenReturn(bank);
        when(productService.createProduct(bank.getBankCode(), bank.getProducts())).thenReturn(bank.getProducts());
        when(productRepository.save(product)).thenReturn(product);
        when(officeService.createOffice(bank.getBankCode(), bank.getOffices())).thenReturn(bank.getOffices());
        when(officeRepository.save(office)).thenReturn(office);
        when(atmService.createAtm(bank.getBankCode(), bank.getAtms())).thenReturn(bank.getAtms());
        when(atmRepository.save(atm)).thenReturn(atm);
        when(bankRepository.save(bank)).thenReturn(bank);
        when(bankMapper.toBankDto(bank)).thenReturn(bankDTO);

        BankDTO actualBank = bankService.create(bankDTO);

        assertNotNull(actualBank);
        verify(bankRepository).save(bank);
        assertEquals(bankDTO.getId(), actualBank.getId());
        assertEquals(bankDTO.getBankCode(), actualBank.getBankCode());
        assertEquals(bankDTO.getIban(), actualBank.getIban());
        assertEquals(bankDTO.getState(), actualBank.getState());
        assertEquals(bankDTO.getCity(), actualBank.getCity());
        assertEquals(bankDTO.getStreet(), actualBank.getStreet());
        assertEquals(bankDTO.getProducts(), actualBank.getProducts());
        assertEquals(bankDTO.getOffices(), actualBank.getOffices());
        assertEquals(bankDTO.getAtms(), actualBank.getAtms());
    }

    @Test
    void createBankReturnedTransactionException() {
        Bank bank = getBankProvider();
        BankDTO bankDTO = getBankDtoProvider();
        Product product = ProductProvider.getProductProvider();
        Office office = OfficeProvider.getOfficeProvider();
        ATM atm = AtmProvider.getAtmProvider();
        when(bankRepository.existsBankByBankName(bankDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByBankCode(bankDTO.getBankCode())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByIban(bankDTO.getIban())).thenReturn(Boolean.FALSE);
        when(bankMapper.toBank(bankDTO)).thenReturn(bank);
        when(productService.createProduct(bank.getBankCode(), bank.getProducts())).thenReturn(bank.getProducts());
        when(productRepository.save(product)).thenReturn(product);
        when(officeService.createOffice(bank.getBankCode(), bank.getOffices())).thenReturn(bank.getOffices());
        when(officeRepository.save(office)).thenReturn(office);
        when(atmService.createAtm(bank.getBankCode(), bank.getAtms())).thenReturn(bank.getAtms());
        when(atmRepository.save(atm)).thenReturn(atm);
        when(bankRepository.save(bank)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> bankService.create(bankDTO));
    }

    @Test
    void createBankIsBankNameNotUniqueUnDB() {
        BankDTO bankDTO = getBankDtoProvider();
        when(bankRepository.existsBankByBankName(bankDTO.getBankName())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> bankService.create(bankDTO));
    }

    @Test
    void createBankIsBankCodeNotUniqueUnDB() {
        BankDTO bankDTO = getBankDtoProvider();
        when(bankRepository.existsBankByBankName(bankDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByBankCode(bankDTO.getBankCode())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> bankService.create(bankDTO));
    }

    @Test
    void createBankIsUserIbanCodeNotUniqueUnDB() {
        BankDTO bankDTO = getBankDtoProvider();
        when(bankRepository.existsBankByBankName(bankDTO.getBankName())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByBankCode(bankDTO.getBankCode())).thenReturn(Boolean.FALSE);
        when(bankRepository.existsBankByIban(bankDTO.getIban())).thenReturn(Boolean.TRUE).thenThrow(CustomDataAlreadyExistsException.class);

        assertThrows(CustomDataAlreadyExistsException.class, () -> bankService.create(bankDTO));
    }
}
