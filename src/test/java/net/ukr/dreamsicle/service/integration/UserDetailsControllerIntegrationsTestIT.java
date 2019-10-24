package net.ukr.dreamsicle.service.integration;

import net.ukr.dreamsicle.dto.UserDetailsDTO;
import net.ukr.dreamsicle.model.User;
import net.ukr.dreamsicle.model.UserDetails;
import net.ukr.dreamsicle.repository.RoleRepository;
import net.ukr.dreamsicle.repository.UserDetailsRepository;
import net.ukr.dreamsicle.repository.UserRepository;
import net.ukr.dreamsicle.service.UserService;
import net.ukr.dreamsicle.util.UserDetailsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Objects;

import static net.ukr.dreamsicle.util.CurrencyProvider.ID;
import static net.ukr.dreamsicle.util.HttpHeaderProvider.getHeader;
import static net.ukr.dreamsicle.util.UserDetailsProvider.*;
import static net.ukr.dreamsicle.util.UserProvider.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.ACCEPTED;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserDetailsControllerIntegrationsTestIT {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Before
    public void initDb() {
        roleRepository.saveAndFlush(ROLE_ADMIN);
        roleRepository.saveAndFlush(ROLE_USER);

        userRepository.saveAndFlush(getUserIntegrationTestForCreateToken(ID, USERNAME_ADMIN, EMAIL_ADMIN, ROLE_ADMIN));
        userRepository.saveAndFlush(getUserIntegrationTestForCreateToken(ID + 1, USERNAME_USER, EMAIL_USER, ROLE_USER));

        TOKEN_ADMIN = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_ADMIN, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
        TOKEN_USER = userService.login(getUsernameAndPasswordIntegrationTest(USERNAME_USER, PASSWORD_WITHOUT_ENCODE)).replace(CAUTION, "");
    }

    private String getRootUrl() {
        return HTTP_LOCALHOST + port;
    }

    @Test
    public void createUserDetailsReturnStatus200OkForAdminRole() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testOkForAdmin", "testOkForAdmin@email.net", ROLE_USER));
        UserDetails userDetails = UserDetailsProvider.getUserDetailsIntegrationProvider(user);

        ResponseEntity<UserDetailsDTO> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_USER), UserDetailsDTO.class, user.getId());

        UserDetailsDTO body = response.getBody();
        assertNotNull(response);
        assertEquals(ACCEPTED, response.getStatusCode());
        assertEquals(userDetails.getSurname(), Objects.requireNonNull(body).getSurname());
        assertEquals(userDetails.getPhone(), Objects.requireNonNull(body).getPhone());
        assertEquals(userDetails.getDescription(), Objects.requireNonNull(body).getDescription());
    }

    @Test
    public void createUserDetailsReturnStatus200OkForUserRole() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testOkForUser", "testOkForUser@email", ROLE_USER));
        UserDetails userDetails = UserDetailsProvider.getUserDetailsIntegrationProvider(user);

        ResponseEntity<UserDetails> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_USER), UserDetails.class, user.getId());

        UserDetails body = response.getBody();
        assertNotNull(response);
        assertEquals(ACCEPTED, response.getStatusCode());
        assertEquals(userDetails.getSurname(), Objects.requireNonNull(body).getSurname());
        assertEquals(userDetails.getPhone(), Objects.requireNonNull(body).getPhone());
        assertEquals(userDetails.getDescription(), Objects.requireNonNull(body).getDescription());
    }

    @Test
    public void createUserDetailsReturnStatus404NotFound() {
        UserDetails userDetails = UserDetailsProvider.getUserDetailsIntegrationProvider(null);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_USER), String.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }

    @Test
    public void createUserDetailsReturnStatus401Unauthorized() {
        ResponseEntity<UserDetails> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, null, UserDetails.class, ID);

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void createUserDetailsNotValidSurnameReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testBadRequestNotValidSurname", "testBadRequestNotValidSurname@email", ROLE_USER));
        UserDetails userDetails = getUserDetailsNotValidDataIntegrationProvider("1", "1234567890", "test Bad Request", user);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for surname"));
    }

    @Test
    public void createUserDetailsNotValidPhoneReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testBadRequestNotValidPhone", "testBadRequestNotValidPhone@email", ROLE_USER));
        UserDetails userDetails = getUserDetailsNotValidDataIntegrationProvider("testBadRequest", "tesdf", "test Bad Request", user);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for phone"));
    }

    @Test
    public void createUserDetailsNotValidDescriptionReturnStatus400OkBadRequest() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testBadRequestNotValidDescription", "testBadRequestNotValidDescription@email", ROLE_USER));
        UserDetails userDetails = getUserDetailsNotValidDataIntegrationProvider("testBadRequest", "1234567890", "123456", user);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetails, TOKEN_ADMIN), String.class, user.getId());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Please input valid data for description"));
    }

    @Test
    public void updateUserDetailsReturnStatus200OkForAdminRole() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testUpdateOkForAdmin", "testUpdateOkForAdmin@email", ROLE_USER));
        userDetailsRepository.saveAndFlush(UserDetailsProvider.getUserDetailsIntegrationProvider(user));
        UserDetails userDetailsUpdate = UserDetailsProvider.getUserDetailsIntegrationProvider(user);

        ResponseEntity<UserDetails> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetailsUpdate, TOKEN_ADMIN), UserDetails.class, user.getId());

        UserDetails body = response.getBody();
        assertNotNull(response);
        assertEquals(ACCEPTED, response.getStatusCode());
        assertEquals(userDetailsUpdate.getSurname(), Objects.requireNonNull(body).getSurname());
        assertEquals(userDetailsUpdate.getPhone(), Objects.requireNonNull(body).getPhone());
        assertEquals(userDetailsUpdate.getDescription(), Objects.requireNonNull(body).getDescription());
    }

    @Test
    public void updateUserDetailsReturnStatus404NotFound() {
        User user = userRepository.saveAndFlush(getUserIntegrationTest("testUpdateNotFound", "testUpdateNotFound@email", ROLE_USER));
        userDetailsRepository.saveAndFlush(UserDetailsProvider.getUserDetailsIntegrationProvider(user));
        UserDetails userDetailsUpdate = UserDetailsProvider.getUserDetailsIntegrationProvider(null);

        ResponseEntity<String> response = testRestTemplate.exchange(getRootUrl() + USER + BY_ID + DETAILS, POST, getHeader(userDetailsUpdate, TOKEN_ADMIN), String.class, Integer.MAX_VALUE);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("Resource not found"));
    }
}
