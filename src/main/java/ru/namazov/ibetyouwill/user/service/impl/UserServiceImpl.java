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
        if(userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            throw new IllegalArgumentException("User with login = " + user.getLogin() + " already registered");
        }

        if(userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email = " + user.getEmail() + " already registered");
        }
        Role roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Current User Role not found"));
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = save(user);

        log.info("IN register - user : {} successfully registered", savedUser);

        return savedUser;
    }

    @Override
    public User deactivate(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User with login = " + login + " not found"));
        userByLogin.setStatus(UserStatus.DISABLE);
        User deactivatedUser = save(userByLogin);

        log.info("IN deactivate user {} successfully deactivated", deactivatedUser);

        return deactivatedUser;
    }

    @Override
    public User findByLogin(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User with login = " + login + " not found"));

        log.info("IN findByLogin - user {} founded by login: {}", userByLogin, login);

        return userByLogin;
    }

    @Override
    public void delete(String login) {
        User userByLogin = findByLogin(login);
        userRepository.delete(userByLogin);

        log.info("IN delete user {} successfully deleted", userByLogin);
    }

    @Override
    public User passwordChange(User user, String newPassword) {
        User dbUser = findByLogin(user.getLogin());
        if (!dbUser.getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("User email = " + user.getEmail() + " is incorrect");
        }
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new IllegalArgumentException("User password = " + user.getPassword() + " is incorrect");
        }
        dbUser.setPassword(passwordEncoder.encode(newPassword));
        User updatedUser = userRepository.save(dbUser);

        log.info("IN passwordChange - user {} successfully updated to user {}", user, updatedUser);

        return updatedUser;
    }

    @Override
    public User emailChange(User user, String newEmail) {
        User dbUser = findByLogin(user.getLogin());
        if (!dbUser.getEmail().equals(user.getEmail())) {
            throw new IllegalArgumentException("User email = " + user.getEmail() + " is incorrect");
        }
        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new IllegalArgumentException("User password = " + user.getPassword() + " is incorrect");
        }
        dbUser.setEmail(newEmail);
        User updatedUser = userRepository.save(dbUser);

        log.info("IN emailChange - user {} successfully updated to user {}", user, updatedUser);

        return updatedUser;
    }

    @Override
    public User save(User user) {
        User savedUser = userRepository.save(user);

        log.info("IN save user {} successfully saved", user);

        return savedUser;
    }

}
