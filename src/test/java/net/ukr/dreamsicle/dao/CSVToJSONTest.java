package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class CSVToJSONTest {

    @Autowired
    private CSVToJSON csvToJSON;

    @Test
    public void readCSVFile() {
        List<Currency> currencies = csvToJSON.readCSVFile(new File("C://TMP//path//Unicredit.csv"));
        currencies.forEach(dev -> System.out.println(currencies.toString()));
    }
}