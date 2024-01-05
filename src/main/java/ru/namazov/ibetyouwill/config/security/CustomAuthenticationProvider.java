package ru.namazov.ibetyouwill.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import ru.namazov.ibetyouwill.user.entity.User;
import ru.namazov.ibetyouwill.user.service.UserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        User myUser = userService.findByLogin(userName);
        if (!password.equals(myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getLogin())
                .password(myUser.getPassword())
                .roles(String.valueOf(myUser.getRoles().get(0)))
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
