package ru.namazov.ibetyouwill.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.namazov.ibetyouwill.config.controller.RestExceptionHandler;
import ru.namazov.ibetyouwill.config.security.jwt.JwtTokenProvider;
import ru.namazov.ibetyouwill.login.dto.AuthenticationRequestDTO;
import ru.namazov.ibetyouwill.login.response.AuthResponse;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@Tag(name = "Authentication", description = "Authentication management APIs")
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Operation(
            summary = "User authentication",
            description = "The authentication request contains userName and userPassword," +
                    "if authentication is successful, the response will be an object with userName and JwtToken")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = { @Content(schema = @Schema(implementation = AuthResponse.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "The User with given login not found.",
                    content = { @Content(schema = @Schema(implementation = RestExceptionHandler.ExceptionResponse.class)) }),
    })
    @PostMapping()
    public ResponseEntity<AuthResponse> login(
            @Parameter(description = "User authentication by AuthRequest")
            @Valid
            @RequestBody
            AuthenticationRequestDTO auth
    ) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUserName(), auth.getUserPassword()));
        User user = userService.findByLogin(auth.getUserName());
        String token = jwtTokenProvider.createToken(user);
        AuthResponse response = new AuthResponse();
        response.setUserName(user.getLogin());
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
}
