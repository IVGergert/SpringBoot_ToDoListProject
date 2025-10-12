package gergert.todo.Controller;

import gergert.todo.DTO.UserProfileDTO;
import gergert.todo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final UserService userService;

    @GetMapping("/profile")
    public String profilePage(Model model) {
        UserProfileDTO userProfile = userService.getCurrentUserProfile();
        model.addAttribute("userProfileDTO", userProfile);
        return "profile";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute("userProfileDTO") UserProfileDTO dto, Model model) {
        String errorMessage = userService.updateProfile(dto);

        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            model.addAttribute("userProfileDTO", dto);
            return "profile";
        }

        model.addAttribute("success", "Профиль успешно обновлён");
        model.addAttribute("userProfileDTO", userService.getCurrentUserProfile());

        userService.updateProfile(dto);
        return "profile";
    }

    // Удалить профиль
    @PostMapping("/profile/delete")
    public String deleteProfile(RedirectAttributes redirectAttributes) {
        userService.deleteProfile();
        redirectAttributes.addFlashAttribute("success", "Аккаунт успешно удалён");
        return "redirect:/login";
    }
}
