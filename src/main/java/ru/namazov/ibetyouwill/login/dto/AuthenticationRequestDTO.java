package ru.namazov.ibetyouwill.login.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String userName;
    private String userPassword;
}
