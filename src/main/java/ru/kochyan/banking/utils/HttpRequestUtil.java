package ru.kochyan.banking.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class HttpRequestUtil {
    public static Cookie findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies.length > 0) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst().orElse(null);
        } else {
            return null;
        }
    }

    public static String getDomain(HttpServletRequest request) {
        return request.getServerName().replaceAll(".*\\.(?=.*\\.)", "");
    }

}
