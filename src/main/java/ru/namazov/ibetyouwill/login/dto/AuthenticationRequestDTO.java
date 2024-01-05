package ru.namazov.ibetyouwill.login.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class AuthenticationRequestDTO {

    @NotBlank
    @Size(min = 3, message = "too short, length Min 3")
    private String userName;

    @NotBlank
    @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
    private String userPassword;
}
