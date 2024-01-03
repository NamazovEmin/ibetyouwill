package ru.namazov.ibetyouwill.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.namazov.ibetyouwill.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLoginAndEmail(String login, String email);

    Optional<User> findUserByLogin(String login);
}
