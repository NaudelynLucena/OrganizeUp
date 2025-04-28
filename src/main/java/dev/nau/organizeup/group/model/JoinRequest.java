package dev.nau.organizeup.group.model;

import dev.nau.organizeup.user.model.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "join_requests")
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User child;

    @ManyToOne(optional = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status;

    private LocalDateTime requestedAt = LocalDateTime.now();

    public JoinRequest() {
    }

    public JoinRequest(User child, Group group) {
        this.child = child;
        this.group = group;
        this.status = RequestStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public User getChild() {
        return child;
    }

    public Group getGroup() {
        return group;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }
}
