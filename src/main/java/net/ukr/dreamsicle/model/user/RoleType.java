package net.ukr.dreamsicle.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Lombok;
import net.ukr.dreamsicle.util.Constants;

/**
 * Simple POJO object that represents application user's role - ADMIN, USER.
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    @Getter
    private String name;

    public static <T extends Enum<T>> RoleType getEnumFromString(String stringValue) {
        if (stringValue != null) {
            try {
                return Enum.valueOf(RoleType.class, stringValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(Constants.ROLE_DOES_NOT_EXIST_PLEASE_CHOOSE_THE_NEXT_ITEMS + RoleType.USER + ", " + RoleType.ADMIN);
            }
        }
        return null;
    }
}
