package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
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
@RequiredArgsConstructor
@RequestMapping
public class ATMController {

    private final AtmService atmService;

    @GetMapping("/atms")
    public Page<AtmDTO> findAll(String search, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return atmService.search(search, page);
    }

    @GetMapping("/atms/{id}")
    public AtmDTO findById(@PathVariable @Min(1) @Positive String id) {
        return atmService.findById(id);
    }

    @PostMapping("/banks/{bankCode}/atms")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AtmDTO create(@PathVariable @Min(1) @Positive String bankCode, @Validated @RequestBody AtmDTO atmDTO) {
        return atmService.create(bankCode, atmDTO);
    }

    @PutMapping("/atms/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public AtmDTO update(@PathVariable @Min(1) @Positive String id, @Validated @RequestBody AtmDTO atmDTO) {
        return atmService.update(id, atmDTO);
    }
}
