package dev.nau.organizeup.group.dto;

import jakarta.validation.constraints.NotBlank;

public class JoinGroupRequest {

    @NotBlank(message = "El código de unión es obligatorio")
    private String joinCode;

    public String getJoinCode() {
        return joinCode;
    }

    public void setJoinCode(String joinCode) {
        this.joinCode = joinCode;
    }
}
