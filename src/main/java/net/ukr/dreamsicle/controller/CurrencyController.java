package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.exception.NotFoundException;
import net.ukr.dreamsicle.model.Currency;
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

    private final CurrencyMapper currencyMapper;
    private final CurrencyService currencyService;

    @GetMapping
    public List<CurrencyDTO> findAll() {
        List<CurrencyDTO> currencyDTOS = currencyMapper.toCurrencyDTOs(currencyService.allCurrenciesData());
        if (currencyDTOS == null) {
            throw new NotFoundException();
        }
        return currencyDTOS;
    }

    @GetMapping("/{id}")
    public CurrencyDTO findById(@PathVariable @Min(1) @Positive int id) {
        Currency currencyById = currencyService.findCurrencyById(id);
        CurrencyDTO currencyDTO = currencyMapper.toCurrencyDto(currencyById);

        if (currencyDTO == null) {
            throw new NotFoundException();
        }
        return currencyDTO;
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
    public CurrencyDTO create(@Validated @RequestBody CurrencyDTO currencyDTO) {
        Integer currency = currencyService.createCurrency(currencyMapper.toCurrency(currencyDTO));

        return currencyMapper.toCurrencyDto(currencyService.findCurrencyById(currency));
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CurrencyDTO update(@PathVariable @Min(1) @Positive Integer id, @Validated @RequestBody CurrencyDTO currencyDTO) {

        Currency currencyById = currencyService.findCurrencyById(id);

        if (currencyById == null){
            throw new NotFoundException();
        }

        Currency currency = currencyMapper.toCurrency(currencyDTO);
        currency.setId(id);
        currencyService.updateCurrency(id, currency);

        return currencyMapper.toCurrencyDto(currencyService.findCurrencyById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive int id) {
        currencyService.deleteCurrencyById(id);
    }
}