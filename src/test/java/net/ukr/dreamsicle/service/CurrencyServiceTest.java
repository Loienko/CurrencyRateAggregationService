package net.ukr.dreamsicle.service;

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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;
    @Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCurrenciesData() {
        int id = 1;
        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(id), CurrencyProvider.getCurrencyProvider(++id));
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(currencies);

        List<Currency> allCurrenciesData = currencyService.getAllCurrenciesData();

        verify(currencyRepositoryDAO).getFindAllCurrency();
        assertEquals(id, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencies, allCurrenciesData);
    }

    @Test
    void testGetFindCurrencyById() {
        int id = 1;
        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        when(currencyRepositoryDAO.getFindCurrencyById(id)).thenReturn(currency);

        Currency actualCurrency = currencyService.getFindCurrencyById(id);

        verify(currencyRepositoryDAO).getFindCurrencyById(id);
        assertEquals(currency, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currency.getId(), actualCurrency.getId());
        assertEquals(currency.getBankName(), actualCurrency.getBankName());
        assertEquals(currency.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testGetCreateCurrency() {
        int id = 1;
        Currency currency = CurrencyProvider.getCurrencyProvider(id);

        currencyService.getCreateCurrency(currency);
        when(currencyRepositoryDAO.getFindCurrencyById(id)).thenReturn(currency);

        verify(currencyRepositoryDAO, times(id)).getCreateCurrency(currency);

        Currency currencyById = currencyService.getFindCurrencyById(id);

        verify(currencyRepositoryDAO).getFindCurrencyById(id);
        assertEquals(currency, currencyById);
        assertNotNull(currencyById);
        assertEquals(currency.getId(), currencyById.getId());
        assertEquals(currency.getBankName(), currencyById.getBankName());
        assertEquals(currency.getCurrencyCode(), currencyById.getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), currencyById.getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), currencyById.getSaleOfCurrency());
    }

    @Test
    void testGetDeleteCurrencyById() {
        int id = 1;
        currencyService.getDeleteCurrencyById(id);

        verify(currencyRepositoryDAO).getDeleteCurrencyById(id);
        doNothing().doThrow(RuntimeException.class).when(currencyRepositoryDAO).getDeleteCurrencyById(id);
        assertNull(currencyService.getFindCurrencyById(id));
    }

    @Test
    void testGetUpdateCurrency(){
        int id = 1;
        int idForUpdate = 2;
        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        Currency currencyForUpdate = CurrencyProvider.getCurrencyProvider(idForUpdate);

        currencyService.getCreateCurrency(currency);
        when(currencyRepositoryDAO.getFindCurrencyById(id)).thenReturn(currency);
        Currency currencyById = currencyService.getFindCurrencyById(id);

        verify(currencyRepositoryDAO, times(id)).getCreateCurrency(currency);
        assertNotNull(currencyById);

        currencyService.getUpdateCurrency(id, currencyForUpdate);
        when(currencyRepositoryDAO.getFindCurrencyById(idForUpdate)).thenReturn(currencyForUpdate);
        Currency currencyByIdSecond = currencyService.getFindCurrencyById(idForUpdate);
        assertNotNull(currencyByIdSecond);

        assertEquals(currencyForUpdate.getId(), currencyByIdSecond.getId());
        assertEquals(currencyForUpdate.getBankName(), currencyByIdSecond.getBankName());
        assertEquals(currencyForUpdate.getCurrencyCode(), currencyByIdSecond.getCurrencyCode());
        assertEquals(currencyForUpdate.getPurchaseCurrency(), currencyByIdSecond.getPurchaseCurrency());
        assertEquals(currencyForUpdate.getSaleOfCurrency(), currencyByIdSecond.getSaleOfCurrency());
    }
}