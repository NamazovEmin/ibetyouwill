package ru.namazov.ibetyouwill.user.service;

import org.springframework.stereotype.Service;

import ru.namazov.ibetyouwill.exception.IllegalArgumentException;
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
        User savedUser = userRepository.save(user);

        log.info("User service save " + savedUser );

        return savedUser;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
