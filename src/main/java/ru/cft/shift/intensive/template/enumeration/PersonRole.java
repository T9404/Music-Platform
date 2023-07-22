package ru.cft.shift.intensive.template.enumeration;

public enum PersonRole {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    PersonRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
