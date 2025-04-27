package dev.nau.organizeup.reward.model;

import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.user.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id")
    private Long id;

    @Column(name = "reward_name", nullable = false)
    private String name;

    @Column(name = "reward_description", length = 500)
    private String description;

    @Column(name = "points_cost", nullable = false)
    private int pointsCost;

    @ManyToOne(optional = true)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by")
    private User createdBy;

    public Reward() {
    }

    public Reward(String name, String description, int pointsCost, Group group, User createdBy) {
        this.name = name;
        this.description = description;
        this.pointsCost = pointsCost;
        this.group = group;
        this.createdBy = createdBy;
    }


    public Long getId() {
        return id;
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

    public int getPointsCost() {
        return pointsCost;
    }

    public void setPointsCost(int pointsCost) {
        this.pointsCost = pointsCost;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
