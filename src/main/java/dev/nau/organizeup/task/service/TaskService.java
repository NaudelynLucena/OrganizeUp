package dev.nau.organizeup.task.service;

import dev.nau.organizeup.exception.ForbiddenActionException;
import dev.nau.organizeup.group.model.Group;
import dev.nau.organizeup.group.repository.GroupRepository;
import dev.nau.organizeup.task.dto.CreateTaskRequest;
import dev.nau.organizeup.task.dto.UpdateTaskRequest;
import dev.nau.organizeup.task.model.Task;
import dev.nau.organizeup.task.repository.TaskRepository;
import dev.nau.organizeup.user.model.Role;
import dev.nau.organizeup.user.model.User;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Task createTask(CreateTaskRequest request, String creatorEmail) {
        User creator = userRepository.findByEmail(creatorEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Group group = null;
        if (request.getGroupId() != null) {
            group = groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
        }

        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                creator,
                request.getPoints());

        task.setGroup(group);

        return taskRepository.save(task);
    }

    public List<Task> getTasksForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Task> personalTasks = taskRepository.findByCreatedBy(user);
        List<Task> assignedTasks = taskRepository.findByAssignedTo(user);

        Set<Group> groups = user.getGroups();
        List<Task> groupTasks = groups.stream()
                .flatMap(group -> taskRepository.findByGroup(group).stream())
                .toList();

        return List.of(
                personalTasks,
                assignedTasks,
                groupTasks).stream()
                .flatMap(List::stream)
                .distinct()
                .toList();
    }

    public void completeTask(Long taskId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (task.isCompleted()) {
            throw new ForbiddenActionException("La tarea ya fue completada");
        }

        task.setCompleted(true);

        int points = task.getPoints();
        user.setAccumulatedPoints(user.getAccumulatedPoints() + points);

        userRepository.save(user);
        taskRepository.save(task);
    }

    public Task editTask(Long taskId, UpdateTaskRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getCreatedBy().getId().equals(user.getId()) && user.getRole() != Role.USER) {
            throw new ForbiddenActionException("No tienes permisos para editar esta tarea");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPoints(request.getPoints());

        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getCreatedBy().getId().equals(user.getId()) && user.getRole() != Role.USER) {
            throw new ForbiddenActionException("No tienes permisos para eliminar esta tarea");
        }

        taskRepository.delete(task);
    }
}
