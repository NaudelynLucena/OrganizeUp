package dev.nau.organizeup.exception;

public class ApiException {
    private String message;

    public ApiException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
