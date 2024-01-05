package ru.namazov.ibetyouwill.user.service;

import org.springframework.stereotype.Service;

import ru.namazov.ibetyouwill.user.entity.User;

@Service
public interface UserService {

    User register(User user);

    User passwordChange(User user, String newPassword);

    User findByLogin(String login);

    void delete(String login);

    User deactivate(String login);

    User emailChange(User user, String email);
}
