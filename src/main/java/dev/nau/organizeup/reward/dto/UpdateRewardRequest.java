package dev.nau.organizeup.reward.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateRewardRequest {

    @NotBlank(message = "El nombre de la recompensa es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "El costo en puntos es obligatorio")
    @Min(value = 1, message = "El costo debe ser al menos 1 punto")
    private Integer pointsCost;

    public UpdateRewardRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPointsCost() {
        return pointsCost;
    }

    public void setPointsCost(Integer pointsCost) {
        this.pointsCost = pointsCost;
    }
}
