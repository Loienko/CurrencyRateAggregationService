package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dto.CurrencyDTO;
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

    private static final int ID = 1;

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

        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(ID), CurrencyProvider.getCurrencyProvider(ID));
        when(currencyRepositoryDAO.findAllCurrencies()).thenReturn(currencies);

        List<CurrencyDTO> allCurrenciesData = currencyService.allCurrenciesData();

        verify(currencyRepositoryDAO).findAllCurrencies();
        assertEquals(ID + 1, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencies, allCurrenciesData);
    }

    @Test
    void testFindCurrencyById() {
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        when(currencyRepositoryDAO.findCurrencyById(ID)).thenReturn(currency);

        CurrencyDTO actualCurrency = currencyService.findCurrencyById(ID);

        verify(currencyRepositoryDAO).findCurrencyById(ID);
        assertEquals(currency, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currency.getBankName(), actualCurrency.getBankName());
        assertEquals(currency.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }

    @Test
    void testCreateCurrency() {
        Currency currency = CurrencyProvider.getCurrencyProvider(ID);
        doNothing().when(currencyRepositoryDAO).createCurrency(eq(currency));

        //TODO need to corrected test
        //currencyService.createCurrency(currency);

        verify(currencyRepositoryDAO).createCurrency(captor.capture());
        assertEquals(captor.getValue().getBankName(), currency.getBankName());
    }

    @Test
    void testDeleteCurrencyById() {
        doNothing().when(currencyRepositoryDAO).deleteCurrencyById(eq(ID));

        currencyService.deleteCurrencyById(ID);

        verify(currencyRepositoryDAO).deleteCurrencyById(ID);
    }

    @Test
    void testUpdateCurrency() {
        Currency currencyForUpdate = CurrencyProvider.getCurrencyProvider(ID + 1);
        doNothing().when(currencyRepositoryDAO).updateCurrency(eq(ID), any());

        //TODO need to corrected test
        //currencyService.updateCurrency(ID, currencyForUpdate);

        verify(currencyRepositoryDAO).updateCurrency(eq(ID), captor.capture());
        assertEquals(captor.getValue().getBankName(), currencyForUpdate.getBankName());
    }
}