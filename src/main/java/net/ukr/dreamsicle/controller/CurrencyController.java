package net.ukr.dreamsicle.controller;

import net.ukr.dreamsicle.dao.ReadCSVFile;
import net.ukr.dreamsicle.dao.ReadJSONFile;
import net.ukr.dreamsicle.dao.ReadXMLFile;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.model.ModelForOutData;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class CurrencyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyController.class);

    @Value("${upload.path}")
    private String path;
    @Autowired
    private CurrencyRepository repository;
    @Autowired
    private ReadXMLFile readXmlFile;
    @Autowired
    private ReadCSVFile readCsvFile;
    @Autowired
    private ReadJSONFile readJSONFile;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Currency> all = repository.findAll();
        model.addAttribute("currencies", all);
        return "index";
    }

    @PostMapping("/index")
    public String add(
            @RequestParam("file")
                    MultipartFile file,
            Model model) {

        List<Currency> currencies = null;
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            try {
                switch (FilenameUtils.getExtension(file.getOriginalFilename())) {
                    case "csv":
                        File csvFile = new File(path + file.getOriginalFilename());
                        csvFile.delete();
                        file.transferTo(csvFile);
                        currencies = readCsvFile.readFile(csvFile);
                        break;
                    case "xml":
                        File xmlFile = new File(path + file.getOriginalFilename());
                        xmlFile.delete();
                        file.transferTo(xmlFile);
                        currencies = readXmlFile.readFile(xmlFile);
                        break;
                    case "json":
                        File jsonFile = new File(path + file.getOriginalFilename());
                        jsonFile.delete();
                        file.transferTo(jsonFile);
                        currencies = readJSONFile.readFile(jsonFile);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                LOGGER.info("Error read/get file", e);
            }
        }
        repository.saveAll(currencies);
        model.addAttribute("currencies", repository.findAll());

        return "index";
    }

    @GetMapping("/get")
    public String getBestDealsOnExchangeRates(Model model) {
        Iterator<Currency> iteratorForAllDataForGetUniqueCurrencyCode = repository.findAll().iterator();
        Set<String> set = new HashSet<>();

        iteratorForAllDataForGetUniqueCurrencyCode.forEachRemaining(currency -> set.add(currency.getCurrencyCode()));

        List<ModelForOutData> dates = new ArrayList<>();
        set.forEach(currencyCode -> {
            String maxPurchaseCurrency = repository.findMaxPurchaseCurrency(currencyCode);
            String minSaleOfCurrency = repository.findMinSaleOfCurrency(currencyCode);
            String byBankNameForPurchaseCurrency = repository.findByBankNameForPurchaseCurrency(currencyCode, maxPurchaseCurrency);
            String byBankNameForSaleOfCurrency = repository.findByBankNameForSaleOfCurrency(currencyCode, minSaleOfCurrency);

            dates.add(new ModelForOutData(
                            currencyCode,
                            maxPurchaseCurrency,
                            byBankNameForPurchaseCurrency,
                            minSaleOfCurrency,
                            byBankNameForSaleOfCurrency
                    )
            );
        });

        model.addAttribute("dates", dates);
        return "get";
    }
}



