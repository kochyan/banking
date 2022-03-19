package ru.kochyan.banking.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.kochyan.banking.dtos.UserCredentialsDto;

@Component
public class AuthenticationUtil {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationUtil(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    public Authentication authenticate(UserCredentialsDto dto) throws BadCredentialsException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return authentication;
        } catch (NullPointerException | AuthenticationException e) {
            throw new BadCredentialsException(String.format(
                    "Неудачная попытка аутентификации для логина '%s'. Неверный логин или пароль", dto.getUsername()));
        }
    }
}
