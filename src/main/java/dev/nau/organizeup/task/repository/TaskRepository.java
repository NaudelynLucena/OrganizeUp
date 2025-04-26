package dev.nau.organizeup.task.repository;

import dev.nau.organizeup.task.model.Task;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCreatedBy(User createdBy);
    List<Task> findByGroup(Group group);
    List<Task> findByAssignedTo(User assignedTo);
}
