package net.ukr.dreamsicle.controller;

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

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("currencies", currencyService.getCurrenciesAllData());
        return "index";
    }

    @PostMapping("/index")
    public String add(
            @RequestParam("file") MultipartFile file) {
        currencyService.getCurrenciesDataFromUploadingFile(file);
        return "index";
    }

    @GetMapping("/get")
    public String getBestDealsOnExchangeRates(Model model) {
        model.addAttribute("dates", currencyService.getModelForOutData());
        return "get";
    }
}



