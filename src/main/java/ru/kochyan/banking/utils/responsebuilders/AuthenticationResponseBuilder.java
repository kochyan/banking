package ru.kochyan.banking.utils.responsebuilders;

import org.springframework.security.core.Authentication;
import ru.kochyan.banking.dtos.UserAuthorityDto;
import ru.kochyan.banking.entities.Privilege;
import ru.kochyan.banking.entities.Role;
import ru.kochyan.banking.entities.User;

import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationResponseBuilder {
    public static UserAuthorityDto buildUserAuthority(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        Set<String> privileges = user.getRoles().stream()
                .flatMap(r -> r.getPrivileges().stream())
                .map(Privilege::getName)
                .collect(Collectors.toSet());

        return UserAuthorityDto.builder()
                .username(user.getUsername())
                .roles(roles)
                .privileges(privileges)
                .build();
    }
}
