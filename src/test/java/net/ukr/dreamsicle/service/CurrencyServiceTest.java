package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    @InjectMocks
    private CurrencyService currencyService;

    @Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;

    @Mock
    private Currency currency;

    @Mock
    private List<Currency> currencies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        currency.setId(1);
        currency.setBankName("PUMB");
        currency.setCurrencyCode("USD");
        currency.setPurchaseCurrency("25.12");
        currency.setSaleOfCurrency("25.30");

        currencies.add(currency);
    }

    @Test
    void testGetAllCurrenciesData() {
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(currencies);

        List<Currency> allCurrenciesData = currencyService.getAllCurrenciesData();

        verify(currencyRepositoryDAO).getFindAllCurrency();
        assertEquals(0, allCurrenciesData.size());
        assertNotNull(allCurrenciesData);
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
}