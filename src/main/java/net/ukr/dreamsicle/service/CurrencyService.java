package net.ukr.dreamsicle.service;

import lombok.extern.java.Log;
import net.ukr.dreamsicle.dao.ReadCSVFile;
import net.ukr.dreamsicle.dao.ReadJSONFile;
import net.ukr.dreamsicle.dao.ReadXMLFile;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.model.ModelForOutData;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Log
public class CurrencyService {

    private static final String CSV_FORMAT = "csv";
    private static final String XML_FORMAT = "xml";
    private static final String JSON_FORMAT = "json";

    private final ReadXMLFile readXmlFile;
    private final ReadCSVFile readCsvFile;
    private final ReadJSONFile readJsonFile;
    private final CurrencyRepository repository;

    @Value("${upload.path}")
    private String PATH;

    @Autowired
    public CurrencyService(ReadXMLFile readXmlFile, ReadCSVFile readCsvFile, ReadJSONFile readJsonFile, CurrencyRepository repository) {
        this.readXmlFile = readXmlFile;
        this.readCsvFile = readCsvFile;
        this.readJsonFile = readJsonFile;
        this.repository = repository;
    }

    public void getCurrenciesDataFromUploadingFile(@RequestParam("file") MultipartFile file) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            try {
                switch (FilenameUtils.getExtension(file.getOriginalFilename())) {
                    case CSV_FORMAT:
                        File csvFile = new File(PATH + file.getOriginalFilename());
                        file.transferTo(csvFile);
                        repository.saveAll(readCsvFile.readFile(csvFile));
                        break;
                    case XML_FORMAT:
                        File xmlFile = new File(PATH + file.getOriginalFilename());
                        file.transferTo(xmlFile);
                        repository.saveAll(readXmlFile.readFile(xmlFile));
                        break;
                    case JSON_FORMAT:
                        File jsonFile = new File(PATH + file.getOriginalFilename());
                        file.transferTo(jsonFile);
                        repository.saveAll(readJsonFile.readFile(jsonFile));
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                log.info("Error read/get file \t" + e.getMessage());
            }
        }
    }

    public List<ModelForOutData> getModelForOutData() {
        Set<String> currencyCodes = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(Currency::toString)
                .collect(Collectors.toSet());

        List<ModelForOutData> dates = new ArrayList<>();
        currencyCodes.forEach(currencyCode -> {
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

    public Iterable<Currency> getCurrenciesAllData() {
        return repository.findAll();
    }
}
