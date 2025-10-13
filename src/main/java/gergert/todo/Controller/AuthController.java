package gergert.todo.Controller;

import gergert.todo.DTO.User.RegisterUserDTO;
import gergert.todo.DTO.User.UserLoginDTO;
import gergert.todo.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginDTO", new UserLoginDTO());
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerUserDTO", new RegisterUserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerUserDTO") RegisterUserDTO dto,
                               BindingResult result,
                               Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        String errorMessage = authService.register(dto);

        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "register";
        }

        return "redirect:/login?registered";
    }
}
