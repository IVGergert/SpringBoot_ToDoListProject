package gergert.todo.Service;

import gergert.todo.DTO.User.UserProfileDTO;
import gergert.todo.Entity.User;
import gergert.todo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    public UserProfileDTO getCurrentUserProfile() {
        User currentUser = getCurrentUser();

        return new UserProfileDTO(
                currentUser.getFirstName(),
                currentUser.getLastName(),
                currentUser.getMiddleName(),
                currentUser.getEmail(),
                null,
                currentUser.getCreatedAt()
        );
    }

    @Transactional
    public String updateProfile(UserProfileDTO dto) {
        User currentUser = getCurrentUser();
        currentUser.setFirstName(dto.getFirstName());
        currentUser.setLastName(dto.getLastName());
        currentUser.setMiddleName(dto.getMiddleName());
        currentUser.setEmail(dto.getEmail());

        if (userRepository.existsByEmail(currentUser.getEmail())) {
            Optional<User> userWithEmail = userRepository.findByEmail(dto.getEmail());
            if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(currentUser.getId())) {
                return "Данный email занят другим пользователем. Придумайте пожалуйста другой";
            }
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(currentUser);
        return null;
    }

    @Transactional
    public void deleteProfile() {
        User user = getCurrentUser();
        userRepository.delete(user);
    }
}
