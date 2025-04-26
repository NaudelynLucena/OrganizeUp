package dev.nau.organizeup.task.controller;

import dev.nau.organizeup.task.dto.CreateTaskRequest;
import dev.nau.organizeup.task.dto.TaskResponse;
import dev.nau.organizeup.task.dto.UpdateTaskRequest;
import dev.nau.organizeup.task.model.Task;
import dev.nau.organizeup.task.service.TaskService;
import dev.nau.organizeup.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService, UserRepository userRepository) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Task task = taskService.createTask(request, email);
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getPoints(),
                task.getGroup() != null ? task.getGroup().getName() : null,
                task.getCreatedBy().getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getMyTasks(Authentication authentication) {
        String email = authentication.getName();
        List<Task> tasks = taskService.getTasksForUser(email);

        List<TaskResponse> response = tasks.stream()
                .map(task -> new TaskResponse(
                        task.getId(),
                        task.getTitle(),
                        task.getDescription(),
                        task.isCompleted(),
                        task.getPoints(),
                        task.getGroup() != null ? task.getGroup().getName() : null,
                        task.getCreatedBy().getName()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<String> completeTask(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        taskService.completeTask(id, email);
        return ResponseEntity.ok("Tarea completada y puntos asignados.");
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<TaskResponse> editTask(@PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        Task task = taskService.editTask(id, request, email);

        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getPoints(),
                task.getGroup() != null ? task.getGroup().getName() : null,
                task.getCreatedBy().getName());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        taskService.deleteTask(id, email);
        return ResponseEntity.ok("Tarea eliminada exitosamente.");
    }
}
