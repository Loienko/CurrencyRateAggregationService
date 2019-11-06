package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.service.AtmService;
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
@AllArgsConstructor
@RequestMapping("/atms")
public class ATMController {

    private AtmService atmService;

    @GetMapping
    public Page<AtmDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return atmService.getAll(page);
    }

    @GetMapping("/{id}")
    public AtmDTO findById(@PathVariable @Min(1) @Positive String id) {
        return atmService.findById(id);
    }

    @PostMapping("/{bankName}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AtmDTO create(@PathVariable @Min(1) @Positive String bankName, @Validated @RequestBody AtmDTO atmDTO) {
        return atmService.create(bankName, atmDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public AtmDTO update(@PathVariable @Min(1) @Positive String id, @Validated @RequestBody AtmDTO atmDTO) {
        return atmService.update(id, atmDTO);
    }
}
