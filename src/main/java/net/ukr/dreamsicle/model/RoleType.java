package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RoleType {
    ADMIN("admin"),
    USER("user"),
    PM("pm");

    @Getter
    private String name;
}
