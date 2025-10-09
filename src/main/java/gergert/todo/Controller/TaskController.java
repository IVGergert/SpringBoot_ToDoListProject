package gergert.todo.Controller;

import gergert.todo.DTO.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    @GetMapping("/tasks")
    public String loginPage() {
        return "tasks";
    }


}