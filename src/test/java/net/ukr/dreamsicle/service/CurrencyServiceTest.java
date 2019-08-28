package net.ukr.dreamsicle.service;

import net.ukr.dreamsicle.dao.ReadCSVFile;
import net.ukr.dreamsicle.dao.ReadJSONFile;
import net.ukr.dreamsicle.dao.ReadXMLFile;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrencyServiceTest {
    private MultipartFile multipartFile;
    private Iterable<Currency> currencies;
    private File file;
    private ReadXMLFile readXMLFile = mock(ReadXMLFile.class);
    private ReadCSVFile readCSVFile = mock(ReadCSVFile.class);
    private ReadJSONFile readJSONFile = mock(ReadJSONFile.class);
    private CurrencyRepository repository = mock(CurrencyRepository.class);
    private Currency currency = mock(Currency.class);
    private List list = mock(List.class);

    @Test
    public void getModelForOutDataTest() {
        when(repository.findMaxPurchaseCurrency(currency.getCurrencyCode())).thenReturn(anyString());
        when(repository.findMinSaleOfCurrency(currency.getCurrencyCode())).thenReturn(anyString());
        when(repository.findByBankNameForPurchaseCurrency(anyString(), anyString())).thenReturn(anyString());
        when(repository.findByBankNameForSaleOfCurrency(anyString(), anyString())).thenReturn(anyString());
    }

    @Test
    public void getCurrenciesAllDataTest() {
        when(repository.findAll()).thenReturn(currencies);
        Assert.assertNotNull(repository.findAll());
    }

    @Test
    public void getReadAndSaveFileToDB() {
        when(readJSONFile.readFile(file)).thenReturn(list);
        when(repository.saveAll(readJSONFile.readFile(file))).thenReturn(currencies);

        when(readCSVFile.readFile(file)).thenReturn(list);
        when(repository.saveAll(readCSVFile.readFile(file))).thenReturn(currencies);

        when(readXMLFile.readFile(file)).thenReturn(list);
        when(repository.saveAll(readXMLFile.readFile(file))).thenReturn(currencies);
    }

    @SuppressWarnings("unchecked")
    @Before
    public void createFile() throws IOException {
        file = mock(File.class);
        when(file.createNewFile()).thenReturn(true);
        list = mock(List.class);
        currencies = mock(Iterable.class);
        multipartFile = new MockMultipartFile("test", "orig", null, "test".getBytes());
    }
}



