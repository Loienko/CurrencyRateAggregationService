package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
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
@RequiredArgsConstructor
@RequestMapping
public class OfficeController {
    private final OfficeService officeService;

    @GetMapping("/offices")
    public Page<OfficeDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return officeService.getAll(page);
    }

    @GetMapping("/offices/{id}")
    public OfficeDTO findById(@PathVariable @Min(1) @Positive String id) {
        return officeService.findById(id);
    }

    @PostMapping("/banks/{bankCode}/offices")
    @ResponseStatus(code = HttpStatus.CREATED)
    public OfficeDTO create(@PathVariable @Min(1) @Positive String bankCode, @Validated @RequestBody OfficeDTO officeDTO) {
        return officeService.create(bankCode, officeDTO);
    }

    @PutMapping("/offices/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public OfficeDTO update(@PathVariable @Min(1) @Positive String id, @Validated @RequestBody OfficeDTO officeDTO) {
        return officeService.update(id, officeDTO);
    }
}
