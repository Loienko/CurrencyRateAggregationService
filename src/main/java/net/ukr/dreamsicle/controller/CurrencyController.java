package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDTO> findAll() {
        return currencyService.allCurrencies();
    }

    @GetMapping("/{id}")
    public CurrencyDTO findById(@PathVariable @Min(1) @Positive int id) {
        return currencyService.findCurrencyById(id);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
    public CurrencyDTO create(@Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyService.createCurrency(currencyDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CurrencyDTO update(@PathVariable @Min(1) @Positive Integer id, @Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyService.updateCurrency(id, currencyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive int id) {
        currencyService.deleteCurrencyById(id);
    }
}