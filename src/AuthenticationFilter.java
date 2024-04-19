import db.dao.UserActivationLinkDao;
import db.dao.UserDao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthenticationService;
import service.EmailSendingService;
import service.UserService;

import java.io.IOException;

public class AuthenticationFilter extends HttpFilter {
    private final AuthenticationService authenticationService;

    public AuthenticationFilter() {
        this.authenticationService = new AuthenticationService(new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService()));
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (noAuthenticationRequired(path)) {
            chain.doFilter(request, response);
            return;
        }
        if (authenticationService.authenticate(request)) {
            super.doFilter(request, response, chain);
        } else {
            response.sendRedirect("authError.html");
        }
    }

    private static boolean noAuthenticationRequired(String path) {
        return path.equals("/index.jsp") || path.equals("/login") || path.equals("/register.jsp") || path.equals("/authError.html");
    }
}
