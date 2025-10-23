package gergert.todo.Controller;

import gergert.todo.DTO.User.UserProfileDTO;
import gergert.todo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String saveProfile(@ModelAttribute("userProfileDTO") UserProfileDTO dto,
                              @RequestParam(value = "newPassword", required = false) String newPassword,
                              RedirectAttributes redirectAttributes) {

        if (newPassword != null && !newPassword.trim().isEmpty()) {
            dto.setPassword(newPassword);
        } else {
            dto.setPassword(null);
        }

        String errorMessage = userService.updateProfile(dto);

        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/profile";
        }

        redirectAttributes.addFlashAttribute("success", "Профиль успешно обновлён");

        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(RedirectAttributes redirectAttributes) {
        userService.deleteProfile();
        redirectAttributes.addFlashAttribute("success", "Аккаунт успешно удалён");
        return "redirect:/login";
    }
}
