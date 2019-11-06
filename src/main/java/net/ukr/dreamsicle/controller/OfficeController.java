package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.service.AtmService;
import net.ukr.dreamsicle.service.OfficeService;
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
@RequestMapping("/offices")
public class OfficeController {
    private OfficeService officeService;

    @GetMapping
    public Page<OfficeDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return officeService.getAll(page);
    }

    @GetMapping("/{id}")
    public OfficeDTO findById(@PathVariable @Min(1) @Positive long id) {
        return officeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OfficeDTO create(@Validated @RequestBody OfficeDTO officeDTO) {
        return officeService.create(officeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public OfficeDTO update(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody OfficeDTO officeDTO) {
        return officeService.update(id, officeDTO);
    }
}
