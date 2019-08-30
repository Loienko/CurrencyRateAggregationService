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
        List<Currency> currencies = Arrays.asList(CurrencyProvider.getCurrencyProvider(), CurrencyProvider.getCurrencyProvider());
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(currencies);

        List<Currency> allCurrenciesData = currencyService.getAllCurrenciesData();

        verify(currencyRepositoryDAO).getFindAllCurrency();
        assertEquals(2, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
        assertSame(currencies, allCurrenciesData);
    }

    @Test
    void testGetFindCurrencyById() {
        int id = 1;
        Currency currencyProvider = CurrencyProvider.getCurrencyProvider();
        when(currencyRepositoryDAO.getFindCurrencyById(id)).thenReturn(currencyProvider);

        Currency currencyById = currencyService.getFindCurrencyById(id);

        verify(currencyRepositoryDAO).getFindCurrencyById(id);
        assertEquals(currencyProvider, currencyById);
        assertNotNull(currencyById);
        assertEquals(currencyProvider.getId(), currencyById.getId());
        assertEquals(currencyProvider.getBankName(), currencyById.getBankName());
        assertEquals(currencyProvider.getCurrencyCode(), currencyById.getCurrencyCode());
        assertEquals(currencyProvider.getPurchaseCurrency(), currencyById.getPurchaseCurrency());
        assertEquals(currencyProvider.getSaleOfCurrency(), currencyById.getSaleOfCurrency());
    }
}