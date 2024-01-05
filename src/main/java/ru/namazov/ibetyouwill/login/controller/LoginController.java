package ru.namazov.ibetyouwill.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.namazov.ibetyouwill.config.security.jwt.JwtTokenProvider;
import ru.namazov.ibetyouwill.login.dto.AuthenticationRequestDTO;
import ru.namazov.ibetyouwill.login.response.AuthResponse;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthenticationRequestDTO auth) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getUserPassword()));
        User user = userService.findByLogin(auth.getUserName());
        String token = jwtTokenProvider.createToken(user);
        AuthResponse response = new AuthResponse();
        response.setUserName(user.getLogin());
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}