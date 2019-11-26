package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankUpdateDTO;
import net.ukr.dreamsicle.service.BankService;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import static org.springframework.data.domain.Sort.Direction;

@RestController
@AllArgsConstructor
@RequestMapping("/banks")
public class BankController {

    private BankService bankService;

    @GetMapping
    public Page<BankDTO> findAll(String search, @PageableDefault(sort = {"id"}, direction = Direction.ASC) Pageable page) {
        return bankService.search(search, page);
    }

    @GetMapping("/{id}")
    public BankDTO findById(@PathVariable @Min(1) @Positive ObjectId id) {
        return bankService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankDTO create(@Validated @RequestBody BankDTO bankDTO) {
        return bankService.create(bankDTO);
    }

    @PutMapping("/{bankCode}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public BankDTO update(@PathVariable @Min(1) @Positive String bankCode, @Validated @RequestBody BankUpdateDTO bankUpdateDTO) {
        return bankService.update(bankCode, bankUpdateDTO);
    }
}
