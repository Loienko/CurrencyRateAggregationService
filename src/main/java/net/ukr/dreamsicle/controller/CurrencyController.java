package net.ukr.dreamsicle.controller;

import io.swagger.annotations.*;
import lombok.Lombok;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.currency.CurrencyDTO;
import net.ukr.dreamsicle.model.currency.Currency;
import net.ukr.dreamsicle.service.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * REST controller for work with {@link Currency} data (findAll, findById, create, update, delete)
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Api(value = "/currencies", produces = "application/json")
@RestController
@Slf4j
@RequestMapping("/currencies")
@RequiredArgsConstructor
class CurrencyController {

    private final CurrencyService currencyService;

    @ApiOperation(value = "Returns a {@link Page} of currencies meeting the paging restriction provided in the {@code Pageable} object.", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Page.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", type = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", type = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", type = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(asc|desc). " +
                            "Default sort order is ascending.")
    })
    @GetMapping
    public Page<CurrencyDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return currencyService.findAllCurrencies(page);
    }

    @ApiOperation(value = "Retrieves an currency by its id.", response = CurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = CurrencyDTO.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @GetMapping("/{id}")
    public CurrencyDTO findById(@PathVariable @Min(1) @Positive long id) {
        return currencyService.findCurrencyById(id);
    }

    @ApiOperation(value = "Create currency and flushes changes instantly.", response = CurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = CurrencyDTO.class),
            @ApiResponse(code = 400, message = "Bad request. Not valid data"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CurrencyDTO create(@Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyService.createCurrency(currencyDTO);
    }

    @ApiOperation(value = "The method update currency by id", response = CurrencyDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted", response = CurrencyDTO.class),
            @ApiResponse(code = 400, message = "Bad request. Not valid data"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public CurrencyDTO update(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody CurrencyDTO currencyDTO) {
        return currencyService.updateCurrency(id, currencyDTO);
    }

    @ApiOperation(value = "Deletes the currency with the given id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive long id) {
        currencyService.deleteCurrencyById(id);
    }
}