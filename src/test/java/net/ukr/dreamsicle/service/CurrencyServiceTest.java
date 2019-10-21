package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import net.ukr.dreamsicle.util.CurrencyProvider;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static net.ukr.dreamsicle.util.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.CurrencyProvider.getCurrencyProvider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @Mock
    Page<Currency> currencyPage;
    @Mock
    Page<CurrencyDTO> currencyDTOPage;
    @Mock
    Pageable pageable;
    @InjectMocks
    private CurrencyService currencyService;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAllCurrenciesPositive() {
        when(currencyRepository.findAll(pageable)).thenReturn(currencyPage);
        when(currencyMapper.toCurrencyDTOs(currencyPage)).thenReturn(currencyDTOPage);

        Page<CurrencyDTO> actualCurrency = currencyService.findAllCurrencies(pageable);

        verify(currencyRepository).findAll(pageable);
        assertNotNull(actualCurrency);
        assertEquals(currencyDTOPage, actualCurrency);
    }

    @Test
    void testFindCurrencyByIdPositive() {
        Currency currencyProvider = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.of(currencyProvider));
        when(currencyMapper.toCurrencyDto(currencyProvider)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.findCurrencyById(ID);

        assertEquals(currencyDto, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getId(), actualCurrency.getId());
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testFindCurrencyByIdIsNotPresentInDb() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.findCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyByIdPositive() {
        Currency currencyProvider = getCurrencyProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.of(currencyProvider));
        doNothing().when(currencyRepository).deleteById(ID);

        currencyService.deleteCurrencyById(ID);

        verify(currencyRepository).deleteById(ID);
    }

    @Test
    void testDeleteCurrencyByIdNotPresentCurrencyInDb() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.deleteCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyReturnedTransactionException() {
        Currency currency = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepository.saveAndFlush(currency)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testCreateCurrencyPositive() {
        Currency currency = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepository.saveAndFlush(currency)).thenReturn(currency);
        when(currencyMapper.toCurrencyDto(currency)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.createCurrency(currencyDto);

        verify(currencyRepository).saveAndFlush(currency);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getId(), actualCurrency.getId());
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testCreateCurrencyFailedToCreatedCurrencyWithReturnedTransactionException() {
        Currency currency = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepository.saveAndFlush(currency)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testUpdateCurrencyPositive() {
        Currency currencyForUpdate = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.of(currencyForUpdate));
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepository.saveAndFlush(currencyForUpdate)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrencyDto(currencyForUpdate)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.updateCurrency(ID, currencyDto);

        verify(currencyRepository).saveAndFlush(currencyForUpdate);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getId(), actualCurrency.getId());
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testUpdateCurrencyIsNotPresentInDb() {
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }

    @Test
    void testUpdateCurrencyReturnedTransactionException() {
        Currency currencyForUpdate = getCurrencyProvider();
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyDtoProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.of(currencyForUpdate));
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepository.saveAndFlush(currencyForUpdate)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }
}