package net.ukr.dreamsicle.controller;

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
@RestController
@RequestMapping("/user_details")
@AllArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDetailsDTO createUserDetails(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody UserDetailsDTO userDetailsDTO) {
        return userDetailsService.createUserDetails(id, userDetailsDTO);
    }
}
