package net.ukr.dreamsicle.model;

public enum RoleName {
    ADMIN("ADMIN"), USER("USER");

    private String name;

    RoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
