package ru.namazov.ibetyouwill.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.namazov.ibetyouwill.exceptions.IllegalArgumentException;
import ru.namazov.ibetyouwill.exceptions.NotFoundException;
import ru.namazov.ibetyouwill.user.entity.Role;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.enums.UserStatus;
import ru.namazov.ibetyouwill.user.repository.RoleRepository;
import ru.namazov.ibetyouwill.user.repository.UserRepository;
import ru.namazov.ibetyouwill.user.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        if (userRepository.findUserByLoginAndEmail(user.getLogin(), user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with login = " + user.getLogin() + " and email = " + user.getEmail() + "not found");
        }
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException("Current User Role not found"));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = save(user);

        log.info("IN register - user : {} successfully registered", savedUser);

        return savedUser;
    }

    @Override
    public User update(User user) {
        User dbUser = userRepository.findUserByLogin(user.getLogin())
                .orElseThrow(() -> new NotFoundException("User = " + user + " not found"));
        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User updatedUser = userRepository.save(dbUser);

        log.info("IN update - user {} successfully updated to user {}", user, updatedUser);

        return updatedUser;
    }

    @Override
    public User getByLogin(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User with login = " + login + " not found"));

        log.info("IN getByLogin - user {} founded by login: {}", userByLogin, login);

        return userByLogin;
    }

    @Override
    public void delete(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User with login = " + login + " not found"));
        userRepository.delete(userByLogin);

        log.info("IN delete user {} successfully deleted", userByLogin);
    }

    @Override
    public User deactivate(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User with login = " + login + " not found"));
        userByLogin.setUserStatus(UserStatus.DISABLE);
        User deactivatedUser = userRepository.save(userByLogin);

        log.info("IN deactivate user {} successfully deactivated", deactivatedUser);

        return deactivatedUser;
    }

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);

        log.info("IN save user {} successfully saved", user);

        return savedUser;
    }
}
