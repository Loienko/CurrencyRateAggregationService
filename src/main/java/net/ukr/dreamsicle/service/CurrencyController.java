package net.ukr.dreamsicle.controller;

import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.repository.CurrencyRepository;
import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyRepository repository;

    @GetMapping("/index")
    public String index(Model model) {
        Iterable<Currency> currenciesDataForStartingApp = repository.findAll();
        model.addAttribute("currencies", currenciesDataForStartingApp);
        return "index";
    }

    @PostMapping("/index")
    public String add(
            @RequestParam("file") MultipartFile file,
            Model model) {
        Iterable<Currency> currenciesDataFromUploadingFile = currencyService.getCurrenciesDataFromUploadingFile(file);
        model.addAttribute("currencies", currenciesDataFromUploadingFile);
        return "index";
    }

    @GetMapping("/get")
    public String getBestDealsOnExchangeRates(Model model) {
        model.addAttribute("dates", currencyService.getModelForOutData());
        return "get";
    }
}



