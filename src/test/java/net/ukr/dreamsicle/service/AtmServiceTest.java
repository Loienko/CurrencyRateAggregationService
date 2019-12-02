package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.atm.AtmMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.repository.AtmRepository;
import net.ukr.dreamsicle.repository.BankRepository;
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

import static net.ukr.dreamsicle.util.atm.AtmProvider.*;
import static net.ukr.dreamsicle.util.bank.BankProvider.getBankProvider;
import static net.ukr.dreamsicle.util.bank.BankProvider.getBankProviderWithIdForAtmAndOfficeAndProduct;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AtmServiceTest {

    @InjectMocks
    private AtmService atmService;
    @Mock
    private Page<AtmDTO> atmDTOPage;
    @Mock
    private Page<ATM> atmPage;
    @Mock
    private Pageable pageable;
    @Mock
    private AtmRepository atmRepository;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private AtmMapper atmMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        when(atmRepository.findAll(pageable)).thenReturn(atmPage);
        when(atmMapper.toAtmDTOs(atmPage)).thenReturn(atmDTOPage);

        Page<AtmDTO> actualAtms = atmService.findAll(pageable);

        verify(atmRepository).findAll(pageable);
        assertNotNull(actualAtms);
        assertEquals(atmDTOPage, actualAtms);
    }

    @Test
    void findById() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        when(atmRepository.findById(ID)).thenReturn(Optional.of(atm));
        when(atmMapper.toAtmDto(atm)).thenReturn(atmDTO);

        AtmDTO actualAtm = atmService.findById(ID);

        assertEquals(atmDTO, actualAtm);
        assertNotNull(actualAtm);
        assertEquals(atmDTO.getId(), actualAtm.getId());
        assertEquals(atmDTO.getBankCode(), actualAtm.getBankCode());
        assertEquals(atmDTO.getName(), actualAtm.getName());
        assertEquals(atmDTO.getState(), actualAtm.getState());
        assertEquals(atmDTO.getCity(), actualAtm.getCity());
        assertEquals(atmDTO.getStreet(), actualAtm.getStreet());
        assertEquals(atmDTO.getWorkTimes(), actualAtm.getWorkTimes());
    }

    @Test
    void testFindUserByIdIsNotPresentInDb() {
        when(atmRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atmService.findById(ID));
    }

    @Test
    void create() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(atmMapper.toAtm(atmDTO)).thenReturn(atm);
        when(atmRepository.save(atm)).thenReturn(atm);
        when(bankRepository.save(bank)).thenReturn(bank);
        when(atmMapper.toAtmDto(atm)).thenReturn(atmDTO);

        AtmDTO actualAtm = atmService.create(ID, atmDTO);

        assertNotNull(actualAtm);
        verify(atmRepository).save(atm);
        assertEquals(atmDTO.getId(), actualAtm.getId());
        assertEquals(atmDTO.getBankCode(), actualAtm.getBankCode());
        assertEquals(atmDTO.getName(), actualAtm.getName());
        assertEquals(atmDTO.getState(), actualAtm.getState());
        assertEquals(atmDTO.getCity(), actualAtm.getCity());
        assertEquals(atmDTO.getStreet(), actualAtm.getStreet());
        assertEquals(atmDTO.getWorkTimes(), actualAtm.getWorkTimes());
    }

    @Test
    void createAtmBankIsNotPresentInDb() {
        AtmDTO atmDTO = getAtmDtoProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atmService.create(ID, atmDTO));
    }

    @Test
    void createAtmSaveToBankReturnedTransactionException() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(atmMapper.toAtm(atmDTO)).thenReturn(atm);
        when(atmRepository.save(atm)).thenReturn(atm);
        when(bankRepository.save(bank)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> atmService.create(ID, atmDTO));
    }

    @Test
    void createAtmReturnedTransactionException() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(atmMapper.toAtm(atmDTO)).thenReturn(atm);
        when(atmRepository.save(atm)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> atmService.create(ID, atmDTO));
    }

    @Test
    void updateAtm() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        Bank bank = getBankProviderWithIdForAtmAndOfficeAndProduct();
        when(atmRepository.findById(ID)).thenReturn(Optional.of(atm));
        when(atmMapper.toAtm(atmDTO)).thenReturn(atm);
        when(bankRepository.findBankByBankCode(bank.getBankCode())).thenReturn(Optional.of(bank));
        when(bankRepository.save(bank)).thenReturn(bank);
        when(atmRepository.save(atm)).thenReturn(atm);
        when(atmMapper.toAtmDto(atm)).thenReturn(atmDTO);

        AtmDTO actualAtm = atmService.update(ID, atmDTO);

        assertNotNull(actualAtm);
        verify(atmRepository).save(atm);
        assertEquals(atmDTO.getId(), actualAtm.getId());
        assertEquals(atmDTO.getBankCode(), actualAtm.getBankCode());
        assertEquals(atmDTO.getName(), actualAtm.getName());
        assertEquals(atmDTO.getState(), actualAtm.getState());
        assertEquals(atmDTO.getCity(), actualAtm.getCity());
        assertEquals(atmDTO.getStreet(), actualAtm.getStreet());
        assertEquals(atmDTO.getWorkTimes(), actualAtm.getWorkTimes());
    }

    @Test
    void updateAtmIsNotPresentInDb() {
        when(atmRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atmService.create(ID, getAtmDtoProvider()));
    }

    @Test
    void updateAtmBankIsNotPresentInDb() {
        ATM atm = getAtmProvider();
        AtmDTO atmDTO = getAtmDtoProvider();
        when(atmRepository.findById(ID)).thenReturn(Optional.of(atm));
        when(atmMapper.toAtm(atmDTO)).thenReturn(atm);
        when(bankRepository.findBankByBankCode(atm.getBankCode())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> atmService.create(ID, getAtmDtoProvider()));
    }
}
