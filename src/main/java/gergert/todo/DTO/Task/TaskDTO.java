package gergert.todo.DTO.Task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime deadline;
    private LocalDateTime completedAt;
}
