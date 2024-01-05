package ru.namazov.ibetyouwill;

import java.util.List;

import org.springframework.stereotype.Component;

import ru.namazov.ibetyouwill.user.entity.Role;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.enums.UserStatus;
import ru.namazov.ibetyouwill.user.repository.RoleRepository;
import ru.namazov.ibetyouwill.user.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StarterBD {

    private final RoleRepository roleRepository;
    private final UserService userService;
    public StarterBD(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        addRole();
        addUser();
    }

    private void addRole() {
        Role role = new Role();
        role.setName("USER");
        role.setStatus(UserStatus.ACTIVE);
        roleRepository.save(role);

        log.info("IN addRole - role : {} successfully added", role);
    }

    private void addUser() {
        User user = new User();
        user.setLogin("test");
        user.setEmail("test");
        user.setPassword("test");
        user.setStatus(UserStatus.ACTIVE);
        user.setRoles(List.of(roleRepository.findByName("USER").orElseThrow()));
        User savedUser = userService.save(user);

        log.info("IN addUser - User : {} successfully added", savedUser);
    }
}
