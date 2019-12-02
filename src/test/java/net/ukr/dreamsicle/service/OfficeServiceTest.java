package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.office.OfficeMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.repository.OfficeRepository;
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
import static net.ukr.dreamsicle.util.office.OfficeProvider.getOfficeDTOProvider;
import static net.ukr.dreamsicle.util.office.OfficeProvider.getOfficeProvider;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class OfficeServiceTest {
    @InjectMocks
    private OfficeService officeService;
    @Mock
    private Page<OfficeDTO> officeDTOPage;
    @Mock
    private Page<Office> officePage;
    @Mock
    private Pageable pageable;
    @Mock
    private OfficeRepository officeRepository;
    @Mock
    private BankRepository bankRepository;
    @Mock
    private OfficeMapper officeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        when(officeRepository.findAll(pageable)).thenReturn(officePage);
        when(officeMapper.toOfficeDTOs(officePage)).thenReturn(officeDTOPage);

        Page<OfficeDTO> actualOffice = officeService.getAll(pageable);

        verify(officeRepository).findAll(pageable);
        assertNotNull(actualOffice);
        assertEquals(officeDTOPage, actualOffice);
    }

    @Test
    void findById() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        when(officeRepository.findById(ID)).thenReturn(Optional.of(office));
        when(officeMapper.toOfficeDto(office)).thenReturn(officeDTO);

        OfficeDTO actualOffice = officeService.findById(ID);

        assertEquals(officeDTO, actualOffice);
        assertNotNull(actualOffice);
        assertEquals(officeDTO.getId(), actualOffice.getId());
        assertEquals(officeDTO.getBankCode(), actualOffice.getBankCode());
        assertEquals(officeDTO.getName(), actualOffice.getName());
        assertEquals(officeDTO.getState(), actualOffice.getState());
        assertEquals(officeDTO.getCity(), actualOffice.getCity());
        assertEquals(officeDTO.getStreet(), actualOffice.getStreet());
        assertEquals(officeDTO.getWorkTimes(), actualOffice.getWorkTimes());
    }

    @Test
    void testFindUserByIdIsNotPresentInDb() {
        when(officeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> officeService.findById(ID));
    }

    @Test
    void create() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(officeMapper.toOffice(officeDTO)).thenReturn(office);
        when(officeRepository.save(office)).thenReturn(office);
        when(bankRepository.save(bank)).thenReturn(bank);
        when(officeMapper.toOfficeDto(office)).thenReturn(officeDTO);

        OfficeDTO actualOffice = officeService.create(ID, officeDTO);

        assertNotNull(actualOffice);
        verify(officeRepository).save(office);
        assertEquals(officeDTO.getId(), actualOffice.getId());
        assertEquals(officeDTO.getBankCode(), actualOffice.getBankCode());
        assertEquals(officeDTO.getName(), actualOffice.getName());
        assertEquals(officeDTO.getState(), actualOffice.getState());
        assertEquals(officeDTO.getCity(), actualOffice.getCity());
        assertEquals(officeDTO.getStreet(), actualOffice.getStreet());
        assertEquals(officeDTO.getWorkTimes(), actualOffice.getWorkTimes());
    }

    @Test
    void createOfficeBankIsNotPresentInDb() {
        OfficeDTO officeDTO = getOfficeDTOProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> officeService.create(ID, officeDTO));
    }

    @Test
    void createOfficeSaveToBankReturnedTransactionException() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(officeMapper.toOffice(officeDTO)).thenReturn(office);
        when(officeRepository.save(office)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> officeService.create(ID, officeDTO));
    }

    @Test
    void createOfficeReturnedTransactionException() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        Bank bank = getBankProvider();
        when(bankRepository.findById(ID)).thenReturn(Optional.of(bank));
        when(officeMapper.toOffice(officeDTO)).thenReturn(office);
        when(officeRepository.save(office)).thenReturn(office);
        when(bankRepository.save(bank)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> officeService.create(ID, officeDTO));
    }

    @Test
    void updateOffice() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        Bank bank = getBankProviderWithIdForAtmAndOfficeAndProduct();
        when(officeRepository.findById(ID)).thenReturn(Optional.of(office));
        when(officeMapper.toOffice(officeDTO)).thenReturn(office);
        when(bankRepository.findBankByBankCode(bank.getBankCode())).thenReturn(Optional.of(bank));
        when(bankRepository.save(bank)).thenReturn(bank);
        when(officeRepository.save(office)).thenReturn(office);
        when(officeMapper.toOfficeDto(office)).thenReturn(officeDTO);

        OfficeDTO actualOffice = officeService.update(ID, officeDTO);

        assertNotNull(actualOffice);
        verify(officeRepository).save(office);
        assertEquals(officeDTO.getId(), actualOffice.getId());
        assertEquals(officeDTO.getBankCode(), actualOffice.getBankCode());
        assertEquals(officeDTO.getName(), actualOffice.getName());
        assertEquals(officeDTO.getState(), actualOffice.getState());
        assertEquals(officeDTO.getCity(), actualOffice.getCity());
        assertEquals(officeDTO.getStreet(), actualOffice.getStreet());
        assertEquals(officeDTO.getWorkTimes(), actualOffice.getWorkTimes());
    }

    @Test
    void updateOfficeIsNotPresentInDb() {
        when(officeRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> officeService.create(ID, getOfficeDTOProvider()));
    }

    @Test
    void updateOfficeBankIsNotPresentInDb() {
        Office office = getOfficeProvider();
        OfficeDTO officeDTO = getOfficeDTOProvider();
        when(officeRepository.findById(ID)).thenReturn(Optional.of(office));
        when(officeMapper.toOffice(officeDTO)).thenReturn(office);
        when(bankRepository.findBankByBankCode(office.getBankCode())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> officeService.create(ID, getOfficeDTOProvider()));
    }
}
