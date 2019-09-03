package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import net.ukr.dreamsicle.util.CurrencyProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @Captor
    ArgumentCaptor<Currency> captor;
    @Spy
    List<Currency> currencies = new ArrayList<>();
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
        int items = 0;

        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        currencies.add(currency);

        doNothing().when(currencyRepositoryDAO).getCreateCurrency(eq(currency));
        currencyService.getCreateCurrency(currencies.get(items));

        verify(currencyRepositoryDAO, times(++items)).getCreateCurrency(captor.capture());
        assertEquals(captor.getValue().getBankName(), currency.getBankName());

        assertEquals(items, currencies.size());
        verify(currencies, times(items)).add(currency);
    }

    @Test
    void testGetDeleteCurrencyById() {
        int id = 1;

        doNothing().doThrow(RuntimeException.class).when(currencyRepositoryDAO).getDeleteCurrencyById(eq(id));
        currencyService.getDeleteCurrencyById(id);
        verify(currencyRepositoryDAO, times(1)).getDeleteCurrencyById(id);
        verify(currencyRepositoryDAO).getDeleteCurrencyById(id);
    }

    @Test
    void testGetUpdateCurrency() {
        int id = 1;
        int idForUpdate = 2;

        Currency currency = CurrencyProvider.getCurrencyProvider(id);
        Currency currencyForUpdate = CurrencyProvider.getCurrencyProvider(idForUpdate);
        currencies.add(currency);

        doNothing().when(currencyRepositoryDAO).getUpdateCurrency(id, currencyForUpdate);
        currencyService.getUpdateCurrency(id, currencyForUpdate);

        doThrow(new RuntimeException()).when(currencyRepositoryDAO).getUpdateCurrency(eq(id), captor.capture());

        verify(currencyRepositoryDAO).getUpdateCurrency(eq(id), captor.capture());
        assertEquals(captor.getValue().getBankName(), currencyForUpdate.getBankName());
    }
}