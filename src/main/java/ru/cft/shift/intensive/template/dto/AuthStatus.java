package ru.cft.shift.intensive.template.dto;

public class AuthStatus {
    private String status;

    public AuthStatus() {
    }

    public AuthStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
