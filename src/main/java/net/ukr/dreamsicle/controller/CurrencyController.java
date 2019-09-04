package net.ukr.dreamsicle.controller;

import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.CurrencyDTO;
import net.ukr.dreamsicle.dto.CurrencyMapper;
import net.ukr.dreamsicle.model.Currency;
import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/currencies")
public class CurrencyController {

    private final CurrencyMapper currencyMapper;
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyMapper currencyMapper, CurrencyService currencyService) {
        this.currencyMapper = currencyMapper;
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> findAll() {
        return ResponseEntity.ok(currencyMapper.toCurrencyDTOs(currencyService.allCurrenciesData()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyDTO> findById(@PathVariable int id) {
        Currency currencyById = currencyService.findCurrencyById(id);

        return ResponseEntity.ok(currencyMapper.toCurrencyDto(currencyById));
    }

    @PostMapping
    public ResponseEntity<CurrencyDTO> create(@RequestBody CurrencyDTO currencyDTO) {
        currencyService.createCurrency(currencyMapper.toCurrency(currencyDTO));

        return ResponseEntity.status(HttpStatus.CREATED).body(currencyDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyDTO> update(@PathVariable int id, @RequestBody CurrencyDTO currencyDTO) {
        Currency currency = currencyMapper.toCurrency(currencyDTO);
        currency.setId(id);
        currencyService.updateCurrency(id, currency);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(currencyDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        currencyService.deleteCurrencyById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}