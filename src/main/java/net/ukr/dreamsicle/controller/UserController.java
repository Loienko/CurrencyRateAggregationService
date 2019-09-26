package net.ukr.dreamsicle.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.service.impl.UserServiceImpl;
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
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public Page<UserDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return userServiceImpl.findAllUsers(page);
    }

    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable @Min(1) @Positive int id) {
        return userServiceImpl.findUserById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO create(@Validated @RequestBody UserDTO userDTO) {
        return userServiceImpl.createUser(userDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDTO update(@PathVariable @Min(1) @Positive Integer id, @Validated @RequestBody UserDTO userDTO) {
        return userServiceImpl.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive int id) {
        userServiceImpl.deleteUser(id);
    }
}