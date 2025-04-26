package dev.nau.organizeup.group.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateGroupRequest {

    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
