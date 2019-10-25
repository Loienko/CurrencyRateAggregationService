package net.ukr.dreamsicle.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Lombok;
import net.ukr.dreamsicle.dto.UserDetailsDTO;
import net.ukr.dreamsicle.model.UserDetails;
import net.ukr.dreamsicle.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * REST controller for create {@link UserDetails} data
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Api(value = "/user", produces = "application/json")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @ApiOperation(value = "The method creates user details by {@code id} and status is {@code ACTIVE}. Saves an user details and flushes changes instantly.")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad request. Not valid data."),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @PostMapping("/{id}/details")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDetailsDTO createUserDetails(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody UserDetailsDTO userDetailsDTO) {
        return userDetailsService.createUserDetails(id, userDetailsDTO);
    }
}
