package ru.namazov.ibetyouwill.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.namazov.ibetyouwill.user.dto.UserDTO;
import ru.namazov.ibetyouwill.user.dto.UserEmailChangeDTO;
import ru.namazov.ibetyouwill.user.dto.UserPasswordChangeDTO;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.mapper.UserMapper;
import ru.namazov.ibetyouwill.user.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    ResponseEntity<UserDTO> register(
            @Valid
            @RequestBody
            UserDTO userDTO
    ) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userService.register(user);
        UserDTO responseUserDTO = userMapper.toDTO(savedUser);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PutMapping("/deactivate/{login}")
    ResponseEntity<UserDTO> deactivate(
            @Valid
            @NotBlank(message = "cannot be empty or consist of spaces")
            @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
            @PathVariable
            String login
    ) {
        User deactivatedUser = userService.deactivate(login);
        UserDTO responseDeactivatedUserDTO = userMapper.toDTO(deactivatedUser);
        return ResponseEntity.ok().body(responseDeactivatedUserDTO);
    }

    @GetMapping("/{login}")
    ResponseEntity<UserDTO> getByLogin(
            @Valid
            @NotBlank(message = "cannot be empty or consist of spaces")
            @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
            @PathVariable String login
    ) {
        User user = userService.findByLogin(login);
        UserDTO responseUserDTO = userMapper.toDTO(user);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @DeleteMapping("/{login}")
    ResponseEntity deleteByLogin(
            @Valid
            @NotBlank(message = "cannot be empty or consist of spaces")
            @Size(min = 3, max = 25, message = "incorrect size, length Min 3, Max 25")
            @PathVariable String login
    ) {
        userService.delete(login);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change/password")
    ResponseEntity<UserDTO> passwordChange(
            @Valid
            @RequestBody
            UserPasswordChangeDTO userPasswordChangeDTO
    ) {
        User user = userMapper.toEntity(userPasswordChangeDTO);
        User updatedUser = userService.passwordChange(user, userPasswordChangeDTO.getNewPassword());
        UserDTO responseUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PutMapping("/change/email")
    ResponseEntity<UserDTO> emailChange(
            @Valid
            @RequestBody
            UserEmailChangeDTO userEmailChangeDTO
    ) {
        User user = userMapper.toEntity(userEmailChangeDTO);
        User updatedUser = userService.emailChange(user, userEmailChangeDTO.getNewEmail());
        UserDTO responseUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok().body(responseUserDTO);
    }
}
