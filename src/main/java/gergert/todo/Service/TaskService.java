package gergert.todo.Service;

import gergert.todo.DTO.TaskDTO;
import gergert.todo.Entity.Task;
import gergert.todo.Entity.User;
import gergert.todo.Repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> getFilteredTasks(User user, String status, String search) {
        List<Task> tasks = taskRepository.findByUser(user);

        if (search != null && !search.isBlank()) {
            tasks = tasks.stream()
                    .filter(t -> t.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                            (t.getDescription() != null && t.getDescription().toLowerCase().contains(search.toLowerCase())))
                    .toList();
        }

        if ("completed".equals(status)) {
            tasks = tasks.stream().filter(Task::isCompleted).toList();
        } else if ("active".equals(status)) {
            tasks = tasks.stream().filter(t -> !t.isCompleted()).toList();
        }

        return tasks;
    }

    public Task createTask(TaskDTO dto, User user) {
        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .deadline(dto.getDeadline())
                .user(user)
                .build();
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void toggleComplete(Task task) {
        boolean nowCompleted = !task.isCompleted();
        task.setCompleted(nowCompleted);

        if (nowCompleted) {
            task.setCompletedAt(LocalDateTime.now());
        } else {
            task.setCompletedAt(null);
        }

        taskRepository.save(task);
    }

}
