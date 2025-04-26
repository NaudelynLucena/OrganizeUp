package dev.nau.organizeup.task.dto;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Integer points;
    private String groupName;
    private String createdByName;

    public TaskResponse(Long id, String title, String description, boolean completed, Integer points, String groupName,
            String createdByName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.points = points;
        this.groupName = groupName;
        this.createdByName = createdByName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Integer getPoints() {
        return points;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getCreatedByName() {
        return createdByName;
    }
}
