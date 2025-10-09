package gergert.todo.Controller;

import gergert.todo.DTO.UserProfileDTO;
import gergert.todo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String saveProfile(@ModelAttribute("userProfileDTO") UserProfileDTO dto) {
        userService.updateProfile(dto);
        return "redirect:/profile";
    }

    // Удалить профиль
    @PostMapping("/profile/delete")
    public String deleteProfile() {
        userService.deleteProfile();
        return "redirect:/login";
    }
}
