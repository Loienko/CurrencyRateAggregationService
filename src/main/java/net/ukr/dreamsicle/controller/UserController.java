package net.ukr.dreamsicle.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserLoginDto;
import net.ukr.dreamsicle.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;


@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return userService.findAllUsers(page);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable @Min(1) @Positive long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO create(@Validated @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String login(@Validated @RequestBody UserLoginDto userLoginDto) {
        return userService.authenticateUser(userLoginDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDTO update(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive long id) {
        userService.deleteUser(id);
    }
}