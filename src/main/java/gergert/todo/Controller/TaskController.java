package gergert.todo.Controller;

import gergert.todo.DTO.Task.TaskAddDTO;
import gergert.todo.DTO.Task.TaskDTO;
import gergert.todo.DTO.Task.TaskUpdateDTO;
import gergert.todo.Entity.Task;
import gergert.todo.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String listTasks(@RequestParam(required = false) String status,
                            @RequestParam(required = false) String search,
                            Model model) {

        List<Task> tasks = taskService.getFilteredTasks(status, search);
        model.addAttribute("tasks",tasks);
        model.addAttribute("status", status);

        if (!model.containsAttribute("taskDTO")) {
            model.addAttribute("taskDTO", new TaskAddDTO());
        }

        if (!model.containsAttribute("taskUpdateDTO")) {
            model.addAttribute("taskUpdateDTO", new TaskUpdateDTO());
        }

        return "task/tasks";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskDTO", new TaskAddDTO());
        return "task/tasks";
    }

    @PostMapping("/create")
    public String createTask(@Valid @ModelAttribute TaskAddDTO taskDTO,
                             BindingResult result,
                             Model model,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String search) {

        if (result.hasErrors()) {
            List<Task> tasks = taskService.getFilteredTasks(status, search);
            model.addAttribute("tasks", tasks);
            model.addAttribute("taskDTO", new TaskAddDTO());
            model.addAttribute("showModal", true);
            return "task/tasks";
        }

        taskService.createTask(taskDTO);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/toggle/{id}")
    public String toggleComplete(@PathVariable Long id) {
        taskService.toggleComplete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Task task = taskService.getTaskById(id);

        TaskUpdateDTO dto = TaskUpdateDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .completed(task.isCompleted())
                .build();

        model.addAttribute("taskDTO", dto);
        model.addAttribute("taskUpdateDTO", dto);
        model.addAttribute("taskId", id);
        model.addAttribute("tasks", taskService.getFilteredTasks(null, null));
        model.addAttribute("showEditModal", true);

        return "task/tasks";
    }

    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id,
                             @Valid @ModelAttribute("taskUpdateDTO") TaskUpdateDTO dto,
                             BindingResult result,
                             Model model) {

        if (result.hasErrors()) {
            model.addAttribute("taskId", id);
            model.addAttribute("tasks", taskService.getFilteredTasks(null, null));
            model.addAttribute("showEditModal", true);
            return "task/tasks";
        }

        taskService.updateTask(id, dto);
        return "redirect:/tasks";
    }


}