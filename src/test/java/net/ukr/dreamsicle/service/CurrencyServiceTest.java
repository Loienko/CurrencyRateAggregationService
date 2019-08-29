package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    private CurrencyRepositoryDAO currencyRepositoryDAO = mock(CurrencyRepositoryDAO.class);
    private List list = mock(List.class);
    private Currency currency = mock(Currency.class);

    @Test
    void testGetAllCurrenciesData() {
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(list);
    }

    @Test
    void testGetFindCurrencyById() {
        when(currencyRepositoryDAO.getFindCurrencyById(anyInt())).thenReturn(currency);
    }
}