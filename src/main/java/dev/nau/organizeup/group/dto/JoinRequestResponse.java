package dev.nau.organizeup.group.dto;

public class JoinRequestResponse {

    private Long requestId;
    private String childName;
    private String groupName;
    private String status;

    public JoinRequestResponse(Long requestId, String childName, String groupName, String status) {
        this.requestId = requestId;
        this.childName = childName;
        this.groupName = groupName;
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

    public String getChildName() {
        return childName;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getStatus() {
        return status;
    }
}
