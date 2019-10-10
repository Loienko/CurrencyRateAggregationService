package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UsernameAndPasswordDataDTO;
import net.ukr.dreamsicle.model.Role;
import net.ukr.dreamsicle.model.RoleType;
import net.ukr.dreamsicle.model.StatusType;
import net.ukr.dreamsicle.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserProvider {
    public static final long ID = 1;
    public static final StatusType STATUS_TYPE_ACTIVE = StatusType.ACTIVE;
    public static final StatusType STATUS_TYPE_NOT_ACTIVE = StatusType.NOT_ACTIVE;
    public static final StatusType STATUS_TYPE_DELETED = StatusType.DELETED;
    public static final RoleType ROLE_TYPE = RoleType.ADMIN;
    public static final String BEARER = "Bearer ";
    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbm5hIiwiaWF0IjoxNTcwNjQ2MzAyLCJleHAiOjE1NzA3MzI3MDJ9.PhniKHni8XmpthhZX2NhnAvDMC6n_UpFkLxvOVBFfIXyQYo1dU9SkFHGdjbIX_Pix1nPi4OvR_fdyZv1tZqCVA";
    private static final String NAME = "USER";
    private static final String USERNAME = "USERNAME";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = UUID.randomUUID().toString();
    private static final Set<Role> ROLES = new HashSet<>(Collections.singletonList(new Role(ID, RoleType.ADMIN)));
    private static final Set<String> ROLES_STRING = new HashSet<>(Collections.singletonList(RoleType.ADMIN.getName()));
    private static final Timestamp CREATED = Timestamp.valueOf(LocalDateTime.now());
    private static final Timestamp UPDATED = Timestamp.valueOf(LocalDateTime.now());

    public static User getUserProvider(long id, StatusType statusType) {
        return User.builder()
                .id(id)
                .name(NAME)
                .username(USERNAME)
                .email(EMAIL)
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
                .username(USERNAME)
                .email(EMAIL)
                .role(ROLES_STRING)
                .build();
    }

    public static UsernameAndPasswordDataDTO getUsernameAndPasswordDataDTO() {
        return UsernameAndPasswordDataDTO.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
    }
}
