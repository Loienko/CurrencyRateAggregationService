package net.ukr.dreamsicle.service.integration;

import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.service.UserService;
import net.ukr.dreamsicle.util.RestPageImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static net.ukr.dreamsicle.util.CurrencyProvider.CURRENCIES;
import static net.ukr.dreamsicle.util.HttpHeaderProvider.getHeader;
import static net.ukr.dreamsicle.util.UserProvider.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerIntegrationTestIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @Before
    public void initDb() {
        roleRepository.save(ROLE_ADMIN);
        roleRepository.save(ROLE_USER);

        userRepository.save(getUserIntegrationTestForCreateToken(ID, USERNAME_ADMIN, EMAIL_ADMIN, ROLE_ADMIN));
        userRepository.save(getUserIntegrationTestForCreateToken(ID + 1, USERNAME_USER, EMAIL_USER, ROLE_USER));

        TOKEN_ADMIN = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_ADMIN, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
        TOKEN_USER = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_USER, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
    }

    private String getRootUrl() {
        return HTTP_LOCALHOST + port;
    }

    @Test
    public void testGetAllUsersReturnStatus200Ok() {
        ResponseEntity<RestPageImpl<UserDTO>> response = restTemplate.exchange(getRootUrl() + CURRENCIES, GET, null, new ParameterizedTypeReference<RestPageImpl<UserDTO>>() {
        });

        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getContent().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findUserByIdReturnStatus200Ok() {
        ResponseEntity<User> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, GET, null, User.class, ID);
        User body = response.getBody();

        assertNotNull(response);
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(NAME, Objects.requireNonNull(response.getBody()).getName());
        assertEquals(EMAIL_ADMIN, Objects.requireNonNull(body).getEmail());
    }

    @Test
    public void findUserByIdReturnStatus404NotFound() {
        ResponseEntity<User> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, GET, null, User.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateUserReturnStatus202Accepted() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserDtoProvider();

        ResponseEntity<User> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), User.class, user.getId());
        User body = response.getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getId());
        assertEquals(userForUpdate.getName(), Objects.requireNonNull(body).getName());
        assertEquals(userForUpdate.getUsername(), Objects.requireNonNull(body).getUsername());
        assertEquals(userForUpdate.getEmail(), Objects.requireNonNull(body).getEmail());
    }

    @Test
    public void updateUserNotValidNameReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("1", "validUsername", "validEmail@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for name"));
    }

    @Test
    public void updateUserNotInputUsernameReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", " ", "validEmail@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the username"));
    }

    @Test
    public void updateUserNotValidUsernameReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", "1", "validEmail@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for username"));
    }

    @Test
    public void updateUserNotUniqueUsernameReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        User user = userRepository.saveAndFlush(getUserIntegrationTestForCreateToken(ID + 1, USERNAME_USER, EMAIL_USER, ROLE_USER));
        UserDTO userForUpdate = getUserWithInputDataProvider("validNameConflict", USERNAME_ADMIN, EMAIL_ADMIN, ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Username is already in use!"));
    }

    @Test
    public void updateUserNotUniqueEmailReturnStatus409Conflict() {
        userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        User user = userRepository.saveAndFlush(getUserIntegrationTestForCreateToken(ID + 1, USERNAME_USER, "validEmailConflict@ukr.net", ROLE_USER));
        UserDTO userForUpdate = getUserWithInputDataProvider("validNameConflict", "validUserNameConflict", EMAIL_ADMIN, ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Email is already in use!"));
    }

    @Test
    public void updateUserNotInputEmailReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", "validUsername", "", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please fill the email"));
    }

    @Test
    public void updateUserNotValidEmailReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", "validUsername", "@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for email"));
    }

    @Test
    public void updateUserNotValidRoleReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", "validUsername", "validEmail@ukr.net", ROLES_STRING_EMPTY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Role does not exist. Please choose the next items:"));
    }

    @Test
    public void updateUserIsUserNotFoundReturnStatus404NotFound() {
        UserDTO userForUpdate = getUserDtoProvider();

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_ADMIN), String.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }

    @Test
    public void updateUserReturnStatus401Unauthorized() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));
        UserDTO userForUpdate = getUserWithInputDataProvider("validName", "validUsername", "validEmail@ukr.net", ROLES_STRING_USER);

        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, null), UserDTO.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void updateUserReturnStatus403AccessDenied() {
        UserDTO userForUpdate = getUserDtoProvider();

        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, PUT, getHeader(userForUpdate, TOKEN_USER), UserDTO.class, userForUpdate.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteUserReturnStatus204NoContent() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));

        ResponseEntity<User> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, DELETE, getHeader(null, TOKEN_ADMIN), User.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteUserReturnStatus404NotFound() {
        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, DELETE, getHeader(null, TOKEN_ADMIN), String.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }

    @Test
    public void deleteUserReturnStatus401Unauthorized() {
        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, DELETE, null, UserDTO.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void deleteUserReturnStatus403AccessDenied() {
        User user = userRepository.saveAndFlush(getUserProvider(STATUS_TYPE_ACTIVE));

        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS + BY_ID, DELETE, getHeader(null, TOKEN_USER), UserDTO.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void assignPasswordReturnStatus202Accepted() {
        userRepository.saveAndFlush(getUserIntegrationTest("testAssignPasswordAcceptedFirst", "testAssignPasswordAcceptedFirst", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("testAssignPasswordAcceptedSecond", "testAssignPasswordAcceptedSecond", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("testAssignPasswordAcceptedFirst", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void assignPasswordReturnStatus401Unauthorized() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordUnauthorizedFirst", "assignPasswordUnauthorizedFirst", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordUnauthorizedSecond", "assignPasswordUnauthorizedSecond", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("assignPasswordUnauthorizedFirst", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void assignPasswordNotValidUsernameReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestUsernameFirsts", "assignPasswordBadRequestUsernameFirsts@ukr.net", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestUsernameSeconds", "assignPasswordBadRequestUsernameSeconds@ukr.net", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("1", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for username"));
    }

    @Test
    public void assignPasswordNotValidPasswordOneUpperCaseLetterReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailFirst", "assignPasswordBadRequestEmailFirst", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailSecond", "assignPasswordBadRequestEmailSecond", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("assignPasswordBadRequestEmailSecond", "qwerty");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must contain 1 or more uppercase characters"));
    }

    @Test
    public void assignPasswordNotValidPasswordOneNumberReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailFirsts", "assignPasswordBadRequestEmailFirsts@ukr.net", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailSeconds", "assignPasswordBadRequestEmailSeconds@ukr.net", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("assignPasswordBadRequestEmailSeconds", "Qwerty");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must contain 1 or more digit characters"));
    }

    @Test
    public void assignPasswordNotValidPasswordMinimumSixLetterReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailFirstss", "assignPasswordBadRequestEmailFirstss@ukr.net", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordBadRequestEmailSecondss", "assignPasswordBadRequestEmailSecondss@ukr.net", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("assignPasswordBadRequestEmailSecondss", "Qwer");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must be 6 or more characters in length"));
    }

    @Test
    public void assignPasswordReturnStatus404NotFound() {
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordNotFoundFirst", "assignPasswordNotFoundFirst", ROLE_USER));
        userRepository.saveAndFlush(getUserIntegrationTest("assignPasswordNotFoundSecond", "assignPasswordNotFoundSecond", ROLE_USER));
        UsernameAndPasswordDataDTO assignPassword = getUsernameAndPasswordIntegrationTest("assignPasswordNotFoundThird", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + PASSWORD_URL, PUT, getHeader(assignPassword, TOKEN_USER), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }

    @Test
    public void authenticateUserReturnStatus201Created() {
        userRepository.saveAndFlush(getUserIntegrationTest("authenticateFirst", "authenticateFirst", ROLE_USER));
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("authenticateFirst", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains(BEARER));
        assertTrue(Objects.requireNonNull(response.getBody()).contains(CAUTION));
    }

    @Test
    public void authenticateUserReturnStatus404NotFound() {
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("authenticateNotFound", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }

    @Test
    public void authenticateUserNotValidUsernameReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("authenticateBadRequestUsername", "authenticateBadRequestUsername", ROLE_USER));
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("1", PASSWORD_WITHOUT_ENCODE);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for username"));
    }

    @Test
    public void authenticateUserNotValidPasswordOneUpperCaseLetterReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("authenticateBadRequestPassFirst", "authenticateBadRequestPassFirst", ROLE_USER));
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("authenticateFirst", "qwerty");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must contain 1 or more uppercase characters"));
    }

    @Test
    public void authenticateUserNotValidPasswordOneNumberReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("authenticateBadRequestPassSecond", "authenticateBadRequestPassSecond", ROLE_USER));
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("authenticateFirst", "Qwerty");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must contain 1 or more digit characters"));
    }

    @Test
    public void authenticateUserNotValidPasswordMinimumSixLetterReturnStatus400BadRequest() {
        userRepository.saveAndFlush(getUserIntegrationTest("authenticateBadRequestPassThird", "authenticateBadRequestPassThird", ROLE_USER));
        UsernameAndPasswordDataDTO user = getUsernameAndPasswordIntegrationTest("authenticateFirst", "Qwer");

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS + LOGIN, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Password must be 6 or more characters in length"));
    }

    @Test
    public void createUserWithRoleAdminReturnStatus202Accepted() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserRoleAdmin", "createUserRoleAdmin@ukr.net", ROLES_STRING_ADMIN);

        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), UserDTO.class);

        UserDTO body = response.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getName(), Objects.requireNonNull(body).getName());
        assertEquals(user.getUsername(), Objects.requireNonNull(body).getUsername());
        assertEquals(user.getEmail(), Objects.requireNonNull(body).getEmail());
    }

    @Test
    public void createUserWithRoleUserReturnStatus202Accepted() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserRoleUser", "createUserRoleUser@ukr.net", ROLES_STRING_USER);

        ResponseEntity<UserDTO> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), UserDTO.class);

        UserDTO body = response.getBody();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user.getName(), Objects.requireNonNull(body).getName());
        assertEquals(user.getUsername(), Objects.requireNonNull(body).getUsername());
        assertEquals(user.getEmail(), Objects.requireNonNull(body).getEmail());
    }

    @Test
    public void createUserNotUniqueUsernameReturnStatus409Conflict() {
        UserDTO user = getUserWithInputDataProvider(NAME, USERNAME_USER, "NotUniqueUsername@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Username is already in use!"));
    }

    @Test
    public void createUserNotUniqueEmailReturnStatus409Conflict() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserNotUniqueEmail", EMAIL_USER, ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Email is already in use!"));
    }

    @Test
    public void createUserNotValidNameReturnStatus400BadRequest() {
        UserDTO user = getUserWithInputDataProvider("1", USERNAME_USER, EMAIL_USER, ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for name"));
    }

    @Test
    public void createUserNotValidUsernameReturnStatus400BadRequest() {
        UserDTO user = getUserWithInputDataProvider(NAME, "1", EMAIL_USER, ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for username"));
    }

    @Test
    public void createUserNotValidEmailReturnStatus400BadRequest() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserNotValidEmail", "@ukr.net", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for email"));
    }

    @Test
    public void createUserNotValidEmailInputNumberReturnStatus400BadRequest() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserNotValidEmail", "1", ROLES_STRING_USER);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for email"));
    }

    @Test
    public void createUserNotValidRoleReturnStatus400BadRequest() {
        UserDTO user = getUserWithInputDataProvider(NAME, "createUserNotValidRole", "createUserNotValidRole@ukr.net", ROLES_STRING_EMPTY);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USERS, POST, getHeader(user, null), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("ole does not exist. Please choose the next items"));
    }
}
