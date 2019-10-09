package net.ukr.dreamsicle.util;

import net.ukr.dreamsicle.dto.UserDetailsDTO;
import net.ukr.dreamsicle.model.UserDetails;

public class UserDetailsProvider {
    public static final long ID = 1;
    public static final String SURNAME = "ADMIN";
    private static final String PHONE = "1234567890";
    private static final String DESCRIPTION = "ADMIN";

    public static UserDetails getUserDetailsProvider() {
        return UserDetails.builder()
                .id(ID)
                .surname(SURNAME)
                .phone(PHONE)
                .description(DESCRIPTION)
                .user(UserProvider.getUserProvider(UserProvider.ID, UserProvider.STATUS_TYPE_ACTIVE))
                .build();
    }

    public static UserDetailsDTO getUserDetailsDtoProvider() {
        return UserDetailsDTO.builder()
                .surname(SURNAME)
                .phone(PHONE)
                .description(DESCRIPTION)
                .build();
    }
}
