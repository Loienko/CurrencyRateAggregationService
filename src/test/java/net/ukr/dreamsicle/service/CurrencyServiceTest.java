package net.ukr.dreamsicle.service;

import javassist.NotFoundException;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import net.ukr.dreamsicle.util.CurrencyProvider;
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

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    private static final int ID = 1;

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
        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(ID), CurrencyProvider.getCurrencyProvider(ID));
        List<CurrencyDTO> currencyDTOS = Arrays.asList(CurrencyProvider.getCurrencyProvider(), CurrencyProvider.getCurrencyProvider());
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
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyProvider();
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

        assertThrows(NotFoundException.class, () -> currencyService.findCurrencyById(ID));
    }

    @Test
    void testDeleteCurrencyByIdPositive() {
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);
        doNothing().when(currencyRepositoryDAO).deleteCurrencyById(eq(ID));

        currencyService.deleteCurrencyById(ID);

        verify(currencyRepositoryDAO).deleteCurrencyById(ID);
    }

    @Test
    void testDeleteCurrencyByIdNotPresentCurrencyInDb() {
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> currencyService.deleteCurrencyById(ID));
    }

    @Test
    void testCreateCurrencyPositive() {
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyProvider();
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
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyProvider();
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currency);
        when(currencyRepositoryDAO.createCurrency(any())).thenReturn(null);

        assertThrows(NotFoundException.class, () -> currencyService.createCurrency(currencyDto));
    }

    @Test
    void testUpdateCurrencyPositive() {
        Currency currencyForUpdate = CurrencyProvider.getCurrencyProvider(ID + 1);
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currencyForUpdate);
        when(currencyMapper.toCurrency(currencyDto)).thenReturn(currencyForUpdate);
        doNothing().when(currencyRepositoryDAO).updateCurrency(ID, currencyForUpdate);
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
        CurrencyDTO currencyDto = CurrencyProvider.getCurrencyProvider();
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> currencyService.updateCurrency(ID, currencyDto));
    }
}