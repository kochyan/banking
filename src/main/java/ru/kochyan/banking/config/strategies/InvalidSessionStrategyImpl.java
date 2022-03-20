package ru.kochyan.banking.config.strategies;

import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class InvalidSessionStrategyImpl implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            Cookie sessionCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("SESSION"))
                    .findFirst().orElse(null);

            if (sessionCookie != null) {
                sessionCookie.setMaxAge(0);
                sessionCookie.setDomain(request.getServerName().replaceAll(".*\\.(?=.*\\.)", ""));
                sessionCookie.setPath("/");
                sessionCookie.setHttpOnly(true);
                response.addCookie(sessionCookie);
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
