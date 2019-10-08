package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.UserDetailsDTO;
import net.ukr.dreamsicle.service.UserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/profile")
@AllArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @PostMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDetailsDTO createUserDetails(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody UserDetailsDTO userDetailsDTO) {
        return userDetailsService.createUserDetails(id, userDetailsDTO);
    }
}
