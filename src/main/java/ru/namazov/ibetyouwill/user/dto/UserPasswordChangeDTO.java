package ru.namazov.ibetyouwill.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserPasswordChangeDTO {

    private String login;

    private String password;

    private String email;

    private String newPassword;
}
