package net.ukr.dreamsicle.dao;

import net.ukr.dreamsicle.model.Currency;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReadJSONFile {
    List<Currency> currencies = new ArrayList<>();

    private static Currency parseCurrencyObject(JSONObject currency, String file) {
        return new Currency(
                file.split("\\.")[0],
                (String) currency.get("currencyCode"),
                (String) currency.get("purchaseCurrency"),
                (String) currency.get("saleOfCurrency")
        );
    }

    public List<Currency> readJson(String path, String originalFilename) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader fileReader = new FileReader(path)) {
            Object parse = jsonParser.parse(fileReader);
            JSONArray currenciesList = (JSONArray) parse;
            for (Object cur : currenciesList) {
                currencies.add(parseCurrencyObject((JSONObject) cur, originalFilename));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return currencies;
    }
}
