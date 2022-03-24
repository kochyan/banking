package ru.kochyan.banking.config.strategies;

import org.springframework.security.web.session.InvalidSessionStrategy;
import ru.kochyan.banking.utils.HttpRequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InvalidSessionStrategyImpl implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        Cookie sessionCookie = HttpRequestUtil.findCookie(request, "SESSION");

        if (sessionCookie != null) {
            sessionCookie.setMaxAge(0);
            sessionCookie.setDomain(HttpRequestUtil.getDomain(request));
            sessionCookie.setPath("/");
            sessionCookie.setHttpOnly(true);
            response.addCookie(sessionCookie);
        }

        Cookie usernameCookie = HttpRequestUtil.findCookie(request, "username");

        if (usernameCookie != null) {
            usernameCookie.setMaxAge(0);
            usernameCookie.setDomain(HttpRequestUtil.getDomain(request));
            usernameCookie.setPath("/");
            response.addCookie(usernameCookie);
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
