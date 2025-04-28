package dev.nau.organizeup.reward.repository;

import dev.nau.organizeup.reward.model.Reward;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    List<Reward> findByGroup(Group group);
    List<Reward> findByGroupIsNullAndCreatedBy(User createdBy);
}
