package gergert.todo.Controller;

import gergert.todo.DTO.TaskDTO;
import gergert.todo.Entity.Task;
import gergert.todo.Entity.User;
import gergert.todo.Service.TaskService;
import gergert.todo.Service.UserService;
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
    private final UserService userService;

    @GetMapping
    public String listTasks(@RequestParam(required = false) String status,
                            @RequestParam(required = false) String search,
                            Model model) {
        User user = userService.getCurrentUser();
        List<Task> tasks = taskService.getFilteredTasks(user, status, search);
        model.addAttribute("tasks", tasks);
        model.addAttribute("taskDTO", new TaskDTO());
        return "task/tasks";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskDTO", new TaskDTO());
        return "task/tasks";
    }

    @PostMapping("/create")
    public String createTask(@Valid @ModelAttribute TaskDTO taskDTO,
                             BindingResult result,
                             Model model,
                             @RequestParam(required = false) String status,
                             @RequestParam(required = false) String search) {
        if (result.hasErrors()) {
            User user = userService.getCurrentUser();
            List<Task> tasks = taskService.getFilteredTasks(user, status, search);
            model.addAttribute("tasks", tasks);
            model.addAttribute("taskDTO", new TaskDTO());
            model.addAttribute("showModal", true);
            return "task/tasks";
        }

        User user = userService.getCurrentUser();
        taskService.createTask(taskDTO, user);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/toggle/{id}")
    public String toggleComplete(@PathVariable Long id) {
        Task task = taskService.getById(id);
        taskService.toggleComplete(task);
        return "redirect:/tasks";
    }





}