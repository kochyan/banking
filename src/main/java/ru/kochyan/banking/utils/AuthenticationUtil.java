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
import ru.kochyan.banking.enums.LogLevel;
import ru.kochyan.banking.facades.LogFacade;

@Component
public class AuthenticationUtil {
    private final AuthenticationManager authenticationManager;
    private final LogFacade logFacade;

    @Autowired
    public AuthenticationUtil(AuthenticationManager authenticationManager, LogFacade logFacade) {
        this.authenticationManager = authenticationManager;
        this.logFacade = logFacade;
    }


    public Authentication authenticate(UserCredentialsDto dto) throws BadCredentialsException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            logFacade.write(String.format("Успешная аутентификация под логином '%s'", dto.getUsername()), LogLevel.INFO);

            return authentication;
        } catch (NullPointerException | AuthenticationException e) {
            throw new BadCredentialsException(String.format(
                    "Неудачная попытка аутентификации для логина '%s'. Неверный логин или пароль", dto.getUsername()));
        }
    }
}
