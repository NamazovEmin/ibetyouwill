package ru.namazov.ibetyouwill.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private String login;

    private String password;

    private String email;
}
