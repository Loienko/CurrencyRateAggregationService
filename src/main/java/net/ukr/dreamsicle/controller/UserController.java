package net.ukr.dreamsicle.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Lombok;
import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.model.User;
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

/**
 * REST controller for work with {@link User} data (findAll, findById, create, login, update, delete, assignPassword)
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Api(value = "/users", produces = "application/json")
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Returns a {@link Page} of users meeting the paging restriction provided in the {@code Pageable} object and status {@code ACTIVE}.", response = Page.class)
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
                            "Default sort order is ascending by id.")
    })
    @GetMapping
    public Page<UserDTO> findAll(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable page) {
        return userService.findAllUsers(page);
    }

    @ApiOperation(value = "Retrieves an user by its id.", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @GetMapping("/{id}")
    public UserDTO findById(@PathVariable @Min(1) @Positive long id) {
        return userService.findUserById(id);
    }

    @ApiOperation(value = "Create unique user data by username, email and flushes changes instantly.", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad request. Not valid data"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO create(@Validated @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @ApiOperation(value = "The method created token by user data. Throws ResourceNotFoundException if user not present.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Please set your own password!!!", response = String.class),
            @ApiResponse(code = 400, message = "Bad request. Not valid data"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String login(@Validated @RequestBody UsernameAndPasswordDataDTO usernameAndPasswordDataDTO) {
        return userService.login(usernameAndPasswordDataDTO);
    }

    @ApiOperation(value = "The method update user by id and status {@code ACTIVE}", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad request. Not valid data"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public UserDTO update(@PathVariable @Min(1) @Positive long id, @Validated @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @ApiOperation(value = "Changes the status of the user to {@code DELETED} with the given id and status {@code ACTIVE}.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Min(1) @Positive long id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Updates password created by user", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted", response = String.class),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 401, message = "Full authentication is required to access this resource"),
            @ApiResponse(code = 403, message = "Forbidden")
    })
    @PutMapping("/password")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public String assignPassword(@Validated @RequestBody UsernameAndPasswordDataDTO usernameAndPasswordDataDTO) {
        return userService.assignPassword(usernameAndPasswordDataDTO);
    }
}