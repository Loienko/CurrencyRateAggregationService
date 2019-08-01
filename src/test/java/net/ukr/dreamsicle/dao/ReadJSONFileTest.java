package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ReadJSONFileTest {

    @Autowired
    private ReadJSONFile readJSONFile;

    @Test
    public void readJson() {
        List<Currency> currencies = readJSONFile.readJson("C:\\TMP\\path\\OTP.json", "OTP.json");
        currencies.forEach(dev -> System.out.println(currencies.toString()));
    }
}