package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepositoryDAO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CurrencyServiceTest {

    /*@Mock
    private CurrencyRepositoryDAO currencyRepositoryDAO;
    @Mock
    private List<Currency> list;
    @Mock
    private Currency currency;*/

    private CurrencyRepositoryDAO currencyRepositoryDAO = mock(CurrencyRepositoryDAO.class);
    private List list = mock(List.class);
    private Currency currency = mock(Currency.class);

    @Test
    void testGetAllCurrenciesData() {
        list.add(currency);
        verify(list).add(currency);
        assertEquals(0, list.size());
        when(currencyRepositoryDAO.getFindAllCurrency()).thenReturn(list);
    }

    @Test
    void testGetFindCurrencyById() {
        when(currencyRepositoryDAO.getFindCurrencyById(anyInt())).thenReturn(currency);
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
       list.add(new Currency("PrivatBank", "USD", "25.10", "25.25"));
       list.add(new Currency("PUMB", "USD", "25.12", "25.30"));
    }
}