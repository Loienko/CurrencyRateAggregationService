package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceIsStale;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
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

import static net.ukr.dreamsicle.util.CurrencyProvider.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;
    @Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;

    @Mock
    private CurrencyMapper currencyMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAllCurrenciesPositive() {
        List<Currency> currencies = Arrays.asList(getCurrencyProvider(POSITIVE_ID), getCurrencyProvider(POSITIVE_ID));
        List<CurrencyDTO> currencyDTOS = Arrays.asList(getCurrencyProvider(), getCurrencyProvider());
        when(currencyRepositoryDAO.findAllCurrencies()).thenReturn(currencies);
        when(currencyMapper.toCurrencyDTOs(currencies)).thenReturn(currencyDTOS);

        List<CurrencyDTO> allCurrenciesData = currencyService.allCurrencies();

        verify(currencyRepositoryDAO).findAllCurrencies();
        assertEquals(POSITIVE_ID + 1, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencyDTOS, allCurrenciesData);
    }

    @Test
    void testFindCurrencyByIdPositive() {
        Currency currency = getCurrencyProvider(POSITIVE_ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currency);
        when(currencyMapper.toCurrencyDto(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID))).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.findCurrencyById(POSITIVE_ID);

        verify(currencyMapper).toCurrencyDto(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID));
        assertEquals(currencyDto, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testFindCurrencyByIdNotPresentInDb() {
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.findCurrencyById(POSITIVE_ID));
    }

    @Test
    void testDeleteCurrencyByIdPositive() {
        Currency currency = getCurrencyProvider(POSITIVE_ID);
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currency);
        when(currencyRepositoryDAO.deleteCurrencyById(POSITIVE_ID)).thenReturn(POSITIVE_ID);

        currencyService.deleteCurrencyById(POSITIVE_ID);

        verify(currencyRepositoryDAO).deleteCurrencyById(POSITIVE_ID);
    }

    @Test
    void testDeleteCurrencyByIdNotPresentCurrencyInDb() {
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.deleteCurrencyById(POSITIVE_ID));
    }

    @Test
    void testDeleteCurrencyByIdDeleteNotPossibleResourceIsStale() {
        Currency currency = getCurrencyProvider(POSITIVE_ID);
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currency);
        when(currencyRepositoryDAO.deleteCurrencyById(POSITIVE_ID)).thenReturn(ZERO_ID);

        assertThrows(ResourceIsStale.class, () -> currencyService.deleteCurrencyById(POSITIVE_ID));
    }

    @Test
    void testCreateCurrencyPositive() {
        Currency currency = getCurrencyProvider(POSITIVE_ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepositoryDAO.createCurrency(currency)).thenReturn(POSITIVE_ID);
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currency);
        when(currencyMapper.toCurrencyDto(currency)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.createCurrency(currencyDto);

        verify(currencyRepositoryDAO).createCurrency(currency);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testCreateCurrencyFailedNotReturnCreatedCurrency() {
        Currency currency = getCurrencyProvider(POSITIVE_ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepositoryDAO.createCurrency(any())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testUpdateCurrencyPositive() {
        Currency currencyForUpdate = getCurrencyProvider(POSITIVE_ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepositoryDAO.updateCurrency(POSITIVE_ID, currencyForUpdate)).thenReturn(POSITIVE_ID);
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrencyDto(currencyForUpdate)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.updateCurrency(POSITIVE_ID, currencyDto);

        verify(currencyRepositoryDAO).updateCurrency(POSITIVE_ID, currencyForUpdate);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testUpdateCurrencyNotPresentInDb() {
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.updateCurrency(POSITIVE_ID, currencyDto));
    }

    @Test
    void testUpdateCurrencyUpdateNotPossibleResourceIsStale() {
        Currency currencyForUpdate = getCurrencyProvider(POSITIVE_ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(POSITIVE_ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepositoryDAO.updateCurrency(POSITIVE_ID, currencyForUpdate)).thenReturn(ZERO_ID);

        assertThrows(ResourceIsStale.class, () -> currencyService.updateCurrency(POSITIVE_ID, currencyDto));
    }
}