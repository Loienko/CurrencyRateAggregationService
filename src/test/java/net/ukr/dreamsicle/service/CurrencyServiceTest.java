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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        int index = 1;
        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(index), CurrencyProvider.getCurrencyProvider(++index));
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(currencies);

        List<Currency> allCurrenciesData = currencyService.getAllCurrenciesData();

        verify(currencyRepositoryDAO).getFindAllCurrency();
        assertEquals(2, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencies, allCurrenciesData);
    }

    @Test
    void testGetFindCurrencyById() {
        int index = 1;
        Currency currency = CurrencyProvider.getCurrencyProvider(index);
        when(currencyRepositoryDAO.getFindCurrencyById(index)).thenReturn(currency);

        Currency actualCurrency = currencyService.getFindCurrencyById(index);

        verify(currencyRepositoryDAO).getFindCurrencyById(index);
        assertEquals(currency, actualCurrency);
        assertNotNull(actualCurrency);
        assertEquals(currency.getId(), actualCurrency.getId());
        assertEquals(currency.getBankName(), actualCurrency.getBankName());
        assertEquals(currency.getCurrencyCode(), actualCurrency.getCurrencyCode());
        assertEquals(currency.getPurchaseCurrency(), actualCurrency.getPurchaseCurrency());
        assertEquals(currency.getSaleOfCurrency(), actualCurrency.getSaleOfCurrency());
    }
}