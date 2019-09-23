package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.ukr.dreamsicle.util.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.CurrencyProvider.getCurrencyProvider;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @InjectMocks
    Optional<Currency> optional;
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
        List<Currency> currencies = Arrays.asList(getCurrencyProvider(ID), getCurrencyProvider(ID));
        List<CurrencyDTO> currencyDTOS = Arrays.asList(getCurrencyProvider(), getCurrencyProvider());
        when(currencyRepository.findAll()).thenReturn(currencies);
        when(currencyMapper.toCurrencyDTOs(currencies)).thenReturn(currencyDTOS);

        List<CurrencyDTO> allCurrenciesData = currencyService.allCurrencies();

        verify(currencyRepository).findAll();
        assertEquals(ID + 1, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencyDTOS, allCurrenciesData);
        assertEquals(currencyDTOS.get(ID).getId(), allCurrenciesData.get(ID).getId());
    }

    @Test
    void testFindCurrencyByIdPositive() {
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepository.findById(ID)).thenReturn(optional);
        when(currencyMapper.toCurrencyDto(currencyMapper.unwrapOptional(optional))).thenReturn(currencyDto);

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
    void testFindCurrencyByIdNotPresentInDb() {
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.findCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyByIdPositive() {
        when(currencyRepository.findById(ID)).thenReturn(optional);
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
    void testCreateCurrencyPositive() {
        Currency currency = getCurrencyProvider(ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
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
        Currency currency = getCurrencyProvider(ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepository.saveAndFlush(currency)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testUpdateCurrencyPositive() {
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepository.findById(ID)).thenReturn(optional);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyMapper.unwrapOptional(optional)).thenReturn(currencyForUpdate);
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
    void testUpdateCurrencyNotPresentInDb() {
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }

    @Test
    void testUpdateCurrencyFailedToUpdateCurrencyWithReturnedTransactionException() {
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepository.findById(ID)).thenReturn(optional);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyMapper.unwrapOptional(optional)).thenReturn(currencyForUpdate);
        when(currencyRepository.saveAndFlush(currencyForUpdate)).thenThrow(TransactionException.class);

        assertThrows(TransactionException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }
}