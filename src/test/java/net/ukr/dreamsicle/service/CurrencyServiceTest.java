package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import net.ukr.dreamsicle.util.CurrencyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @Captor
    ArgumentCaptor<Currency> captor;
    @InjectMocks
    private CurrencyService currencyService;
    @Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAllCurrenciesData() {
        int id = 1;
        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(id), CurrencyProvider.getCurrencyProvider(++id));
        when(currencyRepositoryDAO.findAllCurrency()).thenReturn(currencies);

        List<Currency> allCurrenciesData = currencyService.allCurrenciesData();

        verify(currencyRepositoryDAO).findAllCurrency();
        assertEquals(id, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencies, allCurrenciesData);
    }

    @Test
    void testFindCurrencyById() {
        int id = 1;
        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        when(currencyRepositoryDAO.findCurrencyById(id)).thenReturn(currency);

        Currency actualCurrency = currencyService.findCurrencyById(id);

        verify(currencyRepositoryDAO).findCurrencyById(id);
        assertEquals(currency, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currency.getId(), actualCurrency.getId());
        assertEquals(currency.getBankName(), actualCurrency.getBankName());
        assertEquals(currency.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testCreateCurrency() {
        int id = 1;
        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        doNothing().when(currencyRepositoryDAO).createCurrency(eq(currency));

        currencyService.createCurrency(currency);

        verify(currencyRepositoryDAO).createCurrency(captor.capture());
        assertEquals(captor.getValue().getBankName(), currency.getBankName());
    }

    @Test
    void testDeleteCurrencyById() {
        int id = 1;
        doNothing().when(currencyRepositoryDAO).deleteCurrencyById(eq(id));

        currencyService.deleteCurrencyById(id);

        verify(currencyRepositoryDAO).deleteCurrencyById(id);
    }

    @Test
    void testUpdateCurrency() {
        int id = 1;
        int idForUpdate = 2;
        Currency currencyForUpdate = CurrencyProvider.getCurrencyProvider(idForUpdate);
        doNothing().when(currencyRepositoryDAO).updateCurrency(eq(id), any());

        currencyService.updateCurrency(id, currencyForUpdate);

        verify(currencyRepositoryDAO).updateCurrency(eq(id), captor.capture());
        assertEquals(captor.getValue().getBankName(), currencyForUpdate.getBankName());
    }
}