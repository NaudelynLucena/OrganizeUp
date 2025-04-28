package dev.nau.organizeup.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public class ProfileDto {

    private Long id;

    @NotBlank(message = "Este campo es obligatorio")
    private String name;

    @Email(message = "Email no válido")
    @NotBlank(message = "Email requerido")
    private String email;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    private int accumulatedPoints;

    public ProfileDto() {
    }

    public ProfileDto(Long id, String name, String email, LocalDate birthDate, int accumulatedPoints) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.accumulatedPoints = accumulatedPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }
}