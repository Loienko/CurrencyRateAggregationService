package net.ukr.dreamsicle.util.user;

import net.ukr.dreamsicle.dto.user.UserDTO;
import net.ukr.dreamsicle.dto.user.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.model.user.Role;
import net.ukr.dreamsicle.model.user.RoleType;
import net.ukr.dreamsicle.model.user.StatusType;
import net.ukr.dreamsicle.model.user.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserProvider {
    public static final long ID = 1L;

    //User data like {name, surname, email}
    public static final String NAME = "test";
    public static final String USERNAME_ADMIN = "admin";
    public static final String USERNAME_USER = "user";
    public static final String EMAIL_ADMIN = "admin@ukr.net";
    public static final String EMAIL_USER = "user@ukr.net";

    //Password
    public static final String PASSWORD_WITHOUT_ENCODE = "Qwerty1";
    public static final String PASSWORD = new BCryptPasswordEncoder().encode("Qwerty1");

    //User roles
    public static final Role ROLE_ADMIN = new Role(ID, RoleType.ADMIN);
    public static final Role ROLE_USER = new Role(ID + 1, RoleType.USER);
    public static final RoleType ROLE_TYPE = RoleType.ADMIN;
    public static final Set<String> ROLES_STRING_ADMIN = new HashSet<>(Collections.singletonList(RoleType.ADMIN.getName()));
    public static final Set<String> ROLES_STRING_USER = new HashSet<>(Collections.singletonList(RoleType.USER.getName()));
    public static final Set<String> ROLES_STRING_EMPTY = new HashSet<>(Collections.singletonList(""));
    private static final Set<Role> ROLES = new HashSet<>(Collections.singletonList(ROLE_ADMIN));

    //Time for created and updated user account
    private static final Timestamp CREATED = Timestamp.valueOf(LocalDateTime.now());
    private static final Timestamp UPDATED = Timestamp.valueOf(LocalDateTime.now());

    // Token data for user
    public static String TOKEN_ADMIN;
    public static String TOKEN_USER;
    public static final String TOKEN_WITH_PASSED_DATE = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU3MTM0MTg0OCwiZXhwIjoxNTcxNDI4MjQ4fQ.cVIX0vZHHIndmf2BbyQesaA6RJwFnxt5UWfqZKju48t_m7d_rQFCQP6O75q5445fUuw1Wn922lFXSfCeCJCn4g";

    //Status for user
    public static final StatusType STATUS_TYPE_ACTIVE = StatusType.ACTIVE;
    public static final StatusType STATUS_TYPE_NOT_ACTIVE = StatusType.NOT_ACTIVE;
    public static final StatusType STATUS_TYPE_DELETED = StatusType.DELETED;

    //url data
    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String USERS = "/users";
    public static final String BY_ID = "/{id}";
    public static final String LOGIN = "/login";
    public static final String PASSWORD_URL = "/password";

    //Description
    public static final String BEARER = "Bearer ";
    public static final String SUCCESSFULLY_COMPLETED = "Successfully completed!";
    public static final String CAUTION = "\nPlease set your own password!!!";

    public static User getUserProvider(StatusType statusType) {
        return User.builder()
                .id(ID)
                .name(NAME)
                .username(USERNAME_ADMIN)
                .email(EMAIL_ADMIN)
                .password(PASSWORD)
                .roles(ROLES)
                .created(CREATED)
                .updated(UPDATED)
                .status(statusType)
                .build();
    }

    public static UserDTO getUserDtoProvider() {
        return UserDTO.builder()
                .id(ID)
                .name(NAME)
                .username(USERNAME_ADMIN)
                .email(EMAIL_ADMIN)
                .role(ROLES_STRING_ADMIN)
                .build();
    }

    public static UsernameAndPasswordDataDTO getUsernameAndPasswordDataDTO() {
        return UsernameAndPasswordDataDTO.builder()
                .username(USERNAME_ADMIN)
                .password(PASSWORD)
                .build();
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(USERNAME_ADMIN, PASSWORD);
    }

    public static UsernameAndPasswordDataDTO getUsernameAndPasswordIntegrationTest(String username, String password) {
        return UsernameAndPasswordDataDTO.builder()
                .username(username)
                .password(password)
                .build();
    }

    public static User getUserIntegrationTestForCreateToken(Long id, String username, String email, Role role) {
        return User.builder()
                .id(id)
                .name(NAME)
                .username(username)
                .email(email)
                .password(PASSWORD)
                .roles(new HashSet<>(Collections.singletonList(role)))
                .created(CREATED)
                .updated(UPDATED)
                .status(STATUS_TYPE_ACTIVE)
                .build();
    }

    public static User getUserIntegrationTest(String username, String email, Role role) {
        return User.builder()
                .name(NAME)
                .username(username)
                .email(email)
                .password(PASSWORD)
                .roles(new HashSet<>(Collections.singletonList(role)))
                .created(CREATED)
                .updated(UPDATED)
                .status(STATUS_TYPE_ACTIVE)
                .build();
    }

    public static UserDTO getUserWithInputDataProvider(String name, String username, String email, Set<String> role) {
        return UserDTO.builder()
                .id(ID)
                .name(name)
                .username(username)
                .email(email)
                .role(role)
                .build();
    }
}
