package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.dto.UserDTO;
import net.ukr.dreamsicle.dto.UserLoginDto;
import net.ukr.dreamsicle.model.Role;
import net.ukr.dreamsicle.model.StatusType;
import net.ukr.dreamsicle.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserProvider {
    public static final long ID = 1;
    public static final StatusType STATUS_TYPE_ACTIVE = StatusType.ACTIVE;
    public static final StatusType STATUS_TYPE_NOT_ACTIVE = StatusType.NOT_ACTIVE;
    public static final StatusType STATUS_TYPE_DELETED = StatusType.DELETED;
    private static final String NAME = "USER";
    private static final String USERNAME = "USERNAME";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "$2a$10$8G8KRm.hSfU44xY.QXOwiesANZVBCB1mRLZgltSn9fLYbG7drdMMK";
    private static final String PASSWORD_PASS = "PASSWORD";
    private static final Set<Role> ROLES = new HashSet<>();
    private static final Set<String> ROLES_STRING = new HashSet<>();
    private static final Timestamp CREATED = Timestamp.valueOf(LocalDateTime.now());
    private static final Timestamp UPDATED = Timestamp.valueOf(LocalDateTime.now());

    public static User getUserProvider(long id, StatusType statusType) {
        return User.builder()
                .id(id)
                .name(NAME)
                .username(USERNAME)
                .email(EMAIL)
                .password(new BCryptPasswordEncoder().encode(PASSWORD_PASS))
                .created(CREATED)
                .updated(UPDATED)
                .status(statusType)
                .roles(ROLES)
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

    public static UserLoginDto getUserLoginDtoProvider() {
        return UserLoginDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }
}
