package net.ukr.dreamsicle.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyService.class);

    @Value("${upload.path}")
    private String path;

    private final ReadXMLFile readXmlFile;

    private final ReadCSVFile readCsvFile;

    private final ReadJSONFile readJSONFile;

    private final CurrencyRepository repository;

    @Autowired
    public CurrencyService(ReadXMLFile readXmlFile, ReadCSVFile readCsvFile, ReadJSONFile readJSONFile, CurrencyRepository repository) {
        this.readXmlFile = readXmlFile;
        this.readCsvFile = readCsvFile;
        this.readJSONFile = readJSONFile;
        this.repository = repository;
    }

    public Iterable<Currency> getCurrenciesDataFromUploadingFile(@RequestParam("file") MultipartFile file) {
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
        return repository.findAll();
    }

    public List<ModelForOutData> getModelForOutData() {
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
        return dates;
    }
}
