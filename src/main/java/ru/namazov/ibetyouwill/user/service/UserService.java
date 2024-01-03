package ru.namazov.ibetyouwill.user.service;

import org.springframework.stereotype.Service;

import ru.namazov.ibetyouwill.user.entity.User;

@Service
public interface UserService {

    User register(User user);

    User update(User user);

    User getByLogin(String login);

    void delete(String login);

    User deactivate(String login);

    User save(User user);
}
