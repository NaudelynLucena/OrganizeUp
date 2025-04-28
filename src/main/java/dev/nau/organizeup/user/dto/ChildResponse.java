package dev.nau.organizeup.user.dto;

public class ChildResponse {
    private Long id;
    private String name;
    private String birthDate;

    public ChildResponse(Long id, String name, String birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
