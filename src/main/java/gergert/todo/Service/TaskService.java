package gergert.todo.Service;

import gergert.todo.DTO.Task.TaskAddDTO;
import gergert.todo.DTO.Task.TaskUpdateDTO;
import gergert.todo.Entity.Task;
import gergert.todo.Entity.User;
import gergert.todo.Repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Task> getFilteredTasks(String status, String search) {
        User currentUser = userService.getCurrentUser();

        boolean hasStatus = status != null && !status.isBlank();
        boolean hasSearch = search != null && !search.isBlank();

        if (hasStatus && hasSearch) {
            boolean completed = status.equalsIgnoreCase("completed");
            return taskRepository.findByUserAndCompletedAndTitleContainingIgnoreCase(currentUser, completed, search);
        }

        if (hasStatus) {
            boolean completed = status.equalsIgnoreCase("completed");
            return taskRepository.findByUserAndCompleted(currentUser, completed);
        }

        if (hasSearch) {
            return taskRepository.findByUserAndTitleContainingIgnoreCase(currentUser, search);
        }

        return taskRepository.findByUser(currentUser);
    }

    public Task getTaskById(Long id) {
        User currentUser = userService.getCurrentUser();
        return taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));
    }

    @Transactional
    public Task createTask(TaskAddDTO dto) {
        User currentUser = userService.getCurrentUser();

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .deadline(dto.getDeadline())
                .user(currentUser)
                .build();

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(Long id, TaskUpdateDTO dto) {
        Task task = getTaskById(id);

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());

        if (task.isCompleted() != dto.isCompleted()) {
            task.setCompleted(dto.isCompleted());
            task.setCompletedAt(dto.isCompleted() ? LocalDateTime.now() : null);
        }

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        User currentUser = userService.getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));

        taskRepository.delete(task);
    }

    @Transactional
    public void toggleComplete(Long id) {
        User currentUser = userService.getCurrentUser();
        Task task = taskRepository.findByIdAndUser(id, currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));

        task.setCompleted(!task.isCompleted());
        task.setCompletedAt(task.isCompleted() ? LocalDateTime.now() : null);

        taskRepository.save(task);
    }


}
