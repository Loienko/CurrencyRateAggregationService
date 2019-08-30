package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    private static Currency currency;
    private static List<Currency> currencies;

    @InjectMocks
    private CurrencyService currencyService;
    @Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;

    @BeforeAll
    static void init() {
        currency = new Currency();
        currencies = new ArrayList<>();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        getCurrencyProvider();
    }

    @Test
    void testGetAllCurrenciesData() {
        currencies.add(getCurrencyProvider());
        currencies.add(getCurrencyProvider());
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
        when(currencyRepositoryDAO.getFindCurrencyById(id)).thenReturn(currency);

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

    private Currency getCurrencyProvider() {
        currency.setId(1);
        currency.setBankName("PUMB");
        currency.setCurrencyCode("USD");
        currency.setPurchaseCurrency("25.12");
        currency.setSaleOfCurrency("25.30");
        return currency;
    }
}