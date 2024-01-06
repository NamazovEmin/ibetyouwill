/*
 * Copyright (c) 2024, TopS BI LLC. All rights reserved.
 * http://www.topsbi.ru
 */

package ru.namazov.ibetyouwill.user.service.impl;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.repository.RoleRepository;
import ru.namazov.ibetyouwill.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void register() {
    }

    @Test
    void deactivate() {
        final String login = "emin";
        final User user = mock(User.class);
        final User expected = mock(User.class);

        when(userRepository.findUserByLogin(login)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(expected);

        final User actual = userService.deactivate(login);

        assertNotNull(actual);
        assertEquals(expected, actual, "asdsd");
        verify(userRepository).findUserByLogin(login);
        verify(userRepository).save(user);
    }

    @Test
    void findByLogin() {
        final String login = "emin";
        final User expected = mock(User.class);
        when(userRepository.findUserByLogin(login)).thenReturn(Optional.ofNullable(expected));

        final User actual = userService.findByLogin(login);

        assertNotNull(actual);
        assertEquals(expected, actual, "asdsd");
        verify(userRepository).findUserByLogin(login);
    }

    @Test
    void delete() {
        final String login = "emin";
        final User expected = mock(User.class);
        when(userRepository.findUserByLogin(login)).thenReturn(Optional.ofNullable(expected));

        userService.delete(login);

        assert expected != null;
        verify(userRepository).delete(expected);
    }

    @Test
    void passwordChange() {
    }

    @Test
    void emailChange() {
    }

    @Test
    void save() {
        final User user = mock(User.class);
        final User expected = mock(User.class);

        when(userRepository.save(user)).thenReturn(expected);

        final User actual = userService.save(user);

        assertNotNull(actual);
        assertEquals(actual, expected, "");
        verify(userRepository).save(user);
    }
}