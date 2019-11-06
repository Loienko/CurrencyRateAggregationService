package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.partner.PartnerDTO;
import net.ukr.dreamsicle.service.PartnerService;
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
@RequestMapping("/partners")
public class PartnerController {

    private PartnerService partnerService;

    @GetMapping
    public Page<PartnerDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return partnerService.getAll(page);
    }

    @GetMapping("/{id}")
    public PartnerDTO findById(@PathVariable @Min(1) @Positive String id) {
        return partnerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PartnerDTO create(@Validated @RequestBody PartnerDTO partnerDTO) {
        return partnerService.create(partnerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public PartnerDTO update(@PathVariable @Min(1) @Positive String id, @Validated @RequestBody PartnerDTO partnerDTO) {
        return partnerService.update(id, partnerDTO);
    }
}
