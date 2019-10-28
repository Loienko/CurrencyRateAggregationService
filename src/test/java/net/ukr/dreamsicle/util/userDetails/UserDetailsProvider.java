package net.ukr.dreamsicle.util.userDetails;

import net.ukr.dreamsicle.dto.userDetails.UserDetailsDTO;
import net.ukr.dreamsicle.model.user.User;
import net.ukr.dreamsicle.model.userDetails.UserDetails;
import net.ukr.dreamsicle.util.user.UserProvider;

public class UserDetailsProvider {
    public static final String SURNAME = "ADMIN";
    public static final String USER = "/user";
    public static final String DETAILS = "/details";
    private static final String PHONE = "1234567890";
    private static final String DESCRIPTION = "ADMIN";

    public static UserDetails getUserDetailsProvider() {
        return UserDetails.builder()
                .surname(SURNAME)
                .phone(PHONE)
                .description(DESCRIPTION)
                .user(UserProvider.getUserProvider(UserProvider.STATUS_TYPE_ACTIVE))
                .build();
    }

    public static UserDetailsDTO getUserDetailsDtoProvider() {
        return UserDetailsDTO.builder()
                .surname(SURNAME)
                .phone(PHONE)
                .description(DESCRIPTION)
                .build();
    }

    public static UserDetails getUserDetailsIntegrationProvider(User user) {
        return UserDetails.builder()
                .surname(SURNAME)
                .phone(PHONE)
                .description(DESCRIPTION)
                .user(user)
                .build();
    }

    public static UserDetails getUserDetailsNotValidDataIntegrationProvider(String surname, String phone, String description, User user) {
        return UserDetails.builder()
                .surname(surname)
                .phone(phone)
                .description(description)
                .user(user)
                .build();
    }
}
