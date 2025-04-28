package dev.nau.organizeup.group.dto;

public class GroupResponse {

    private Long id;
    private String name;
    private String joinCode;

    public GroupResponse(Long id, String name, String joinCode) {
        this.id = id;
        this.name = name;
        this.joinCode = joinCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJoinCode() {
        return joinCode;
    }
}
