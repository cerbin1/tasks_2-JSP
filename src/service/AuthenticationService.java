package service;

import db.dao.UserActivationLinkDao;
import db.dao.UserDao;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.Optional;

public class AuthenticationService {
    private final UserService userService;

    public AuthenticationService() {
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
    }

    public AuthenticationService(UserService userService) {
        this.userService = userService;
    }

    public boolean authenticate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String username = (String) session.getAttribute("username");
        String sessionId = tryGetSessionIdFromCookie(request);
        return userService.userIsLoggedIn(username, sessionId) && userService.userIsActive(username);
    }

    private String tryGetSessionIdFromCookie(HttpServletRequest request) {
        Optional<Cookie> jsessionidCookie = Arrays
                .stream(request.getCookies())
                .filter((name) -> name.getName().equals("JSESSIONID"))
                .findFirst();
        return jsessionidCookie.orElseThrow().getValue();
    }

    public boolean authenticateAdmin(HttpServletRequest request) {
        return authenticate(request) && isAdmin(request);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String username = (String) session.getAttribute("username");
        return username.equals("admin");
    }
}
