package ru.kochyan.banking.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.kochyan.banking.entities.User;

public class SecurityUtil {
    public static User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? (User) authentication.getPrincipal() : null;
    }
}
