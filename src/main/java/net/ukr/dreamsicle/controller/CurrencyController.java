package net.ukr.dreamsicle.controller;

import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("currencies", currencyService.allCurrenciesData());
        return "index";
    }
}



