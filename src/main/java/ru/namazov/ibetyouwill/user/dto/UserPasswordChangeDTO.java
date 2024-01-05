package ru.namazov.ibetyouwill.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserPasswordChangeDTO {

    @NotBlank(message = "cannot be empty or consist of spaces")
    @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
    private String login;

    @NotBlank(message = "cannot be empty or consist of spaces")
    @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
    private String password;

    @Email(message = "incorrect type")
    private String email;

    @NotBlank(message = "cannot be empty or consist of spaces")
    @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
    private String newPassword;
}
