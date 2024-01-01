package ru.namazov.ibetyouwill.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.namazov.ibetyouwill.user.dto.UserDTO;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.mapper.UserMapper;
import ru.namazov.ibetyouwill.user.service.interfaces.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(name = "/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    ResponseEntity<UserDTO> save(UserDTO userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userService.save(user);
        UserDTO responseUserDTO = userMapper.toDTO(savedUser);
        return ResponseEntity.ok().body(responseUserDTO);
    }
}
