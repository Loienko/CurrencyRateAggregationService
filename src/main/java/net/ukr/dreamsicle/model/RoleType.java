package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Lombok;

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
                throw new IllegalArgumentException("Role does not exist. Please choose the next items: " + RoleType.USER + "; " + RoleType.ADMIN);
            }
        }
        return null;
    }
}
