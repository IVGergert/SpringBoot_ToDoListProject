package gergert.todo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @Email(message = "Некорректный email")
    @NotBlank(message = "Введите Email")
    private String email;

    @NotBlank(message = "Введите пароль")
    private String password;

}
