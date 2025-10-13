package gergert.todo.DTO.Task;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TaskAddDTO {
    @NotBlank(message = "Название задачи обязательно")
    private String title;

    private String description;

    private boolean completed = false;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
}
