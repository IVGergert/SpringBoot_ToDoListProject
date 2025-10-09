package gergert.todo.Service;

import gergert.todo.DTO.RegisterUserDTO;
import gergert.todo.Entity.Role;
import gergert.todo.Entity.User;
import gergert.todo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterUserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            return "Данный email занят другим пользователем. Придумайте пожалуйста другой";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return "Пароли не совпадают";
        }

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .role(Role.USER)
                .enabled(true)
                .build();

        userRepository.save(user);
        return null;
    }

}
