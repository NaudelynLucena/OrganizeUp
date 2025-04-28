package dev.nau.organizeup.auth.dto;

public class ChildRegisterResponse {
    private String name;
    private String accessCode;

    public ChildRegisterResponse(String name, String accessCode) {
        this.name = name;
        this.accessCode = accessCode;
    }

    public String getName() {
        return name;
    }

    public String getAccessCode() {
        return accessCode;
    }
}
