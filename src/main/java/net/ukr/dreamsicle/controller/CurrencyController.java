package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.service.impl.CurrencyServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@Slf4j
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyServiceImpl currencyServiceImpl;

    @GetMapping
    public Page<CurrencyDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return currencyServiceImpl.findAllCurrencies(page);
    }

    @GetMapping("/{id}")
    public CurrencyDTO findById(@PathVariable @Min(1) @Positive int id) {
        return currencyServiceImpl.findCurrencyById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CurrencyDTO create(@Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyServiceImpl.createCurrency(currencyDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CurrencyDTO update(@PathVariable @Min(1) @Positive Integer id, @Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyServiceImpl.updateCurrency(id, currencyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive int id) {
        currencyServiceImpl.deleteCurrencyById(id);
    }
}