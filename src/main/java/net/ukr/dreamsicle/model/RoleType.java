package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
