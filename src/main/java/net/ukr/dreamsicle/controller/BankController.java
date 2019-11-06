package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankViewDTO;
import net.ukr.dreamsicle.service.BankService;
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
@RequestMapping("/banks")
public class BankController {

    private BankService bankService;

    @GetMapping
    public Page<BankDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return bankService.getAll(page);
    }

    @GetMapping("/{id}")
    public BankDTO findById(@PathVariable @Min(1) @Positive String id) {
        return bankService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankDTO create(@Validated @RequestBody BankDTO bankDTO) {
        return bankService.create(bankDTO);
    }

    @PutMapping("/{bankName}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public BankDTO update(@PathVariable @Min(1) @Positive String bankName, @Validated @RequestBody BankDTO bankDTO) {
        return bankService.update(bankName, bankDTO);
    }

    //TODO
    // Will implement later
    @GetMapping("search")
    public Page<BankDTO> search(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return bankService.search(page);
    }

    //TODO
    // Just for test app
    @DeleteMapping
    public void delete() {
        bankService.delete();
    }
}
