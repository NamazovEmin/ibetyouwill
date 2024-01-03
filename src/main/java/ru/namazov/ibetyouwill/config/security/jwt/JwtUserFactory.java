package ru.namazov.ibetyouwill.config.security.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ru.namazov.ibetyouwill.user.entity.Role;
import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.enums.UserStatus;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUserDetails create(User user) {
        return new JwtUserDetails(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())),
                user.getUserStatus().equals(UserStatus.ACTIVE),
                user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
