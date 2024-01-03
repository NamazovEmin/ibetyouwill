package ru.namazov.ibetyouwill.user.service;

import org.springframework.stereotype.Service;

import ru.namazov.ibetyouwill.base.enums.Status;
import ru.namazov.ibetyouwill.exception.IllegalArgumentException;
import ru.namazov.ibetyouwill.exception.NotFoundException;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.repository.UserRepository;
import ru.namazov.ibetyouwill.user.service.interfaces.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        if (userRepository.findUserByLoginAndEmail(user.getLogin(), user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This user already exists" + user);
        }
        user.setStatus(Status.ENABLE);
        User savedUser = userRepository.save(user);

        log.info("UserService save " + savedUser );

        return savedUser;
    }

    @Override
    public User update(User user) {
        User dbUser = userRepository.findUserByLogin(user.getLogin())
                .orElseThrow(() -> new NotFoundException("User is not found" + user));
        dbUser.setEmail(user.getEmail());
        dbUser.setPassword(user.getPassword());

        User updatedUser = userRepository.save(dbUser);

        log.info("UserService update " + updatedUser);

        return updatedUser;
    }

    @Override
    public User getByLogin(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User is not found  by login = " + login));

        log.info("UserService getByLogin " + userByLogin);

        return userByLogin;
    }

    @Override
    public void delete(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User is not found  by login = " + login));
        userRepository.delete(userByLogin);

        log.info("UserService delete " + userByLogin);
    }

    @Override
    public User deactivate(String login) {
        User userByLogin = userRepository.findUserByLogin(login).orElseThrow(() -> new NotFoundException("User is not found  by login = " + login));
        userByLogin.setStatus(Status.DISABLE);
        User deactivatedUser = userRepository.save(userByLogin);

        log.info("UserService deactivate " + deactivatedUser);

        return deactivatedUser;
    }
}
