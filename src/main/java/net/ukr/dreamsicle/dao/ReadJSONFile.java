package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReadJSONFile implements ReadFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadJSONFile.class);
    List<Currency> currencies = new ArrayList<>();

    private static Currency parseCurrencyObject(JSONObject currency, String file) {
        return new Currency(
                FilenameUtils.removeExtension(file),
                (String) currency.get("currencyCode"),
                (String) currency.get("purchaseCurrency"),
                (String) currency.get("saleOfCurrency")
        );
    }

    @Override
    public List<Currency> readFile(File jsonFile) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(jsonFile)) {
            Object parse = jsonParser.parse(fileReader);
            JSONArray currenciesList = (JSONArray) parse;
            for (Object cur : currenciesList) {
                currencies.add(parseCurrencyObject((JSONObject) cur, FilenameUtils.removeExtension(jsonFile.getName())));
            }
        } catch (IOException | ParseException e) {
            LOGGER.info("Error", e);
        }
        return currencies;
    }
}
