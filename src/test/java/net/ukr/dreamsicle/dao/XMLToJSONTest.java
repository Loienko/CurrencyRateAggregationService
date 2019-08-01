package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class XMLToJSONTest {

    @Autowired
    private XMLToJSON xmlToJSON;

    @Test
    public void parseCurrencyXML() {
        List<Currency> currencies = xmlToJSON.parseCurrencyXML(new File("C:\\TMP\\path\\PrivatBank.xml"));
        currencies.forEach(dev -> System.out.println(currencies.toString()));
    }
}