package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER");

    @Getter
    private String name;

    public static <T extends Enum<T>> T getEnumFromString(String stringValue) {
        if (RoleType.class != null && stringValue != null) {
            try {
                return Enum.valueOf((Class<T>) RoleType.class, stringValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Enum is allow. Please choose the next items: " + RoleType.USER + "; " + RoleType.ADMIN);
            }
        }
        return null;
    }
}
