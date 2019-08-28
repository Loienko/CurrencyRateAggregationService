package net.ukr.dreamsicle.service;

import lombok.extern.java.Log;
import net.ukr.dreamsicle.dao.ReadCSVFile;
import net.ukr.dreamsicle.dao.ReadFile;
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

    private String maxPurchaseCurrency;
    private String minSaleOfCurrency;
    private String byBankNameForPurchaseCurrency;
    private String byBankNameForSaleOfCurrency;

    private final ModelForOutData modelForOutData;


    @Value("${upload.path}")
    private String PATH;

    @Autowired
    public CurrencyService(ReadXMLFile readXmlFile, ReadCSVFile readCsvFile, ReadJSONFile readJsonFile, CurrencyRepository repository) {
        this.readXmlFile = readXmlFile;
        this.readCsvFile = readCsvFile;
        this.readJsonFile = readJsonFile;
        this.repository = repository;
        this.modelForOutData = new ModelForOutData();
    }

    public void getCurrenciesDataFromUploadingFile(@RequestParam("file") MultipartFile downloadFile) {
        if (downloadFile != null && !downloadFile.getOriginalFilename().isEmpty()) {
            try {
                switch (FilenameUtils.getExtension(downloadFile.getOriginalFilename())) {
                    case CSV_FORMAT:
                        getReadAndSaveFileToDB(downloadFile, readCsvFile);
                        break;
                    case XML_FORMAT:
                        getReadAndSaveFileToDB(downloadFile, readXmlFile);
                        break;
                    case JSON_FORMAT:
                        getReadAndSaveFileToDB(downloadFile, readJsonFile);
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                log.info("Error read/get file \t" + e.getMessage());
            }
        }
    }

    private void getReadAndSaveFileToDB(@RequestParam("file") MultipartFile file, ReadFile readFile) throws IOException {
        File downloadFile = new File(PATH + file.getOriginalFilename());
        file.transferTo(downloadFile);
        repository.saveAll(readFile.readFile(downloadFile));
    }

    public List<ModelForOutData> getModelForOutData() {
        Set<String> currencyCodes = StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(Currency::getCurrencyCode)
                .collect(Collectors.toSet());

        List<ModelForOutData> dataForView = new ArrayList<>();

        currencyCodes.forEach(currencyCode -> {
            maxPurchaseCurrency = repository.findMaxPurchaseCurrency(currencyCode);
            minSaleOfCurrency = repository.findMinSaleOfCurrency(currencyCode);
            byBankNameForPurchaseCurrency = repository.findByBankNameForPurchaseCurrency(currencyCode, maxPurchaseCurrency);
            byBankNameForSaleOfCurrency = repository.findByBankNameForSaleOfCurrency(currencyCode, minSaleOfCurrency);

            modelForOutData.setCode(currencyCode);
            modelForOutData.setCurrencyPurchase(maxPurchaseCurrency);
            modelForOutData.setBankCurrencyPurchase(byBankNameForPurchaseCurrency);
            modelForOutData.setSaleOfCurrency(minSaleOfCurrency);
            modelForOutData.setBankSaleOfCurrency(byBankNameForSaleOfCurrency);

            dataForView.add(modelForOutData);
        });

        return dataForView;
    }

    public Iterable<Currency> getCurrenciesAllData() {
        return repository.findAll();
    }
}
