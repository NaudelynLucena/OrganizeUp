package dev.nau.organizeup.reward.dto;

public class RewardResponse {

    private Long id;
    private String name;
    private String description;
    private int pointsCost;
    private String groupName;
    private String createdByName;

    public RewardResponse(Long id, String name, String description, int pointsCost, String groupName, String createdByName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pointsCost = pointsCost;
        this.groupName = groupName;
        this.createdByName = createdByName;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPointsCost() {
        return pointsCost;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getCreatedByName() {
        return createdByName;
    }
}
