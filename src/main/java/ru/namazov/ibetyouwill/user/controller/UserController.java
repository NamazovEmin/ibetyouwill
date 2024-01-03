package ru.namazov.ibetyouwill.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/deactivate/{login}")
    ResponseEntity<UserDTO> deactivate(@PathVariable String login) {
        User deactivatedUser = userService.deactivate(login);
        UserDTO responseDeactivatedUserDTO = userMapper.toDTO(deactivatedUser);
        return ResponseEntity.ok().body(responseDeactivatedUserDTO);
    }

    @PutMapping
    ResponseEntity<UserDTO> update(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = userService.update(user);
        UserDTO responseUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @GetMapping("/{login}")
    ResponseEntity<UserDTO> getByLogin(@PathVariable String login) {
        User user = userService.getByLogin(login);
        UserDTO responseUserDTO = userMapper.toDTO(user);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @DeleteMapping("/{login}")
    ResponseEntity deleteByLogin(@PathVariable String login) {
        userService.delete(login);
        return ResponseEntity.ok().build();
    }


}
