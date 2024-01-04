package ru.namazov.ibetyouwill.login.response;

import lombok.Data;

@Data
public class AuthResponse {

    private String userName;
    private String token;
}
