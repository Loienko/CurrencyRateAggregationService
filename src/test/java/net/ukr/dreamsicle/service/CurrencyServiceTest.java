package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.ResourceIsStaleException;
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

import static net.ukr.dreamsicle.util.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.CurrencyProvider.getCurrencyProvider;
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
        List<Currency> currencies = Arrays.asList(getCurrencyProvider(ID), getCurrencyProvider(ID));
        List<CurrencyDTO> currencyDTOS = Arrays.asList(getCurrencyProvider(), getCurrencyProvider());
        when(currencyRepositoryDAO.findAllCurrencies()).thenReturn(currencies);
        when(currencyMapper.toCurrencyDTOs(currencies)).thenReturn(currencyDTOS);

        List<CurrencyDTO> allCurrenciesData = currencyService.allCurrencies();

        verify(currencyRepositoryDAO).findAllCurrencies();
        assertEquals(ID + 1, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencyDTOS, allCurrenciesData);
    }

    @Test
    void testFindCurrencyByIdPositive() {
        Currency currency = getCurrencyProvider(ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);
        when(currencyMapper.toCurrencyDto(currencyRepositoryDAO.findCurrencyById(ID))).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.findCurrencyById(ID);

        verify(currencyMapper).toCurrencyDto(currencyRepositoryDAO.findCurrencyById(ID));
        assertEquals(currencyDto, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testFindCurrencyByIdNotPresentInDb() {
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.findCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyByIdPositive() {
        Currency currency = getCurrencyProvider(ID);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);
        when(currencyRepositoryDAO.deleteCurrencyById(ID)).thenReturn(true);

        currencyService.deleteCurrencyById(ID);

        verify(currencyRepositoryDAO).deleteCurrencyById(ID);
    }

    @Test
    void testDeleteCurrencyByIdNotPresentCurrencyInDb() {
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.deleteCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyByIdDeleteNotPossibleResourceIsStale() {
        Currency currency = getCurrencyProvider(ID);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);
        when(currencyRepositoryDAO.deleteCurrencyById(ID)).thenReturn(false);

        assertThrows(ResourceIsStaleException.class, () -> currencyService.deleteCurrencyById(ID));
    }

    @Test
    void testCreateCurrencyPositive() {
        Currency currency = getCurrencyProvider(ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepositoryDAO.createCurrency(currency)).thenReturn(ID);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);
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
        Currency currency = getCurrencyProvider(ID);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepositoryDAO.createCurrency(any())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testUpdateCurrencyPositive() {
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepositoryDAO.updateCurrency(ID, currencyForUpdate)).thenReturn(true);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrencyDto(currencyForUpdate)).thenReturn(currencyDto);

        CurrencyDTO actualCurrency = currencyService.updateCurrency(ID, currencyDto);

        verify(currencyRepositoryDAO).updateCurrency(ID, currencyForUpdate);
        assertNotNull(actualCurrency);
        assertEquals(currencyDto.getBankName(), actualCurrency.getBankName());
        assertEquals(currencyDto.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currencyDto.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currencyDto.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testUpdateCurrencyNotPresentInDb() {
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }

    @Test
    void testUpdateCurrencyUpdateNotPossibleResourceIsStale() {
        Currency currencyForUpdate = getCurrencyProvider(ID + 1);
        CurrencyDTO currencyDto = getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        when(currencyRepositoryDAO.updateCurrency(ID, currencyForUpdate)).thenReturn(false);

        assertThrows(ResourceIsStaleException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }
}