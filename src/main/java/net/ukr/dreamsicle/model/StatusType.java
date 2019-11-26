package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusType {
    ACTIVE("active"),
    NOT_ACTIVE("not_active"),
    DELETED("deleted");

    @Getter
    private String name;

    public static StatusType getEnumFromString(String stringValue) {
        if (stringValue != null) {
            try {
                return Enum.valueOf(StatusType.class, stringValue.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Enum is allow. Please choose the next items: "
                        + StatusType.ACTIVE + "; "
                        + StatusType.NOT_ACTIVE + "; "
                        + StatusType.DELETED);
            }
        }
        return null;
    }
}
