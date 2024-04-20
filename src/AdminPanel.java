import db.dao.UserActivationLinkDao;
import db.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AuthenticationService;
import service.EmailSendingService;
import service.UserService;

import java.io.IOException;

public class AdminPanel extends HttpServlet {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AdminPanel() {
        this.authenticationService = new AuthenticationService(new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService()));
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (authenticationService.authenticateAdmin(request)) {
            request.setAttribute("users", userService.getUsersForAdminPanel());
            request.getServletContext().getRequestDispatcher("/adminPanel.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Authorization failed");
            request.getRequestDispatcher("navbar.jsp").forward(request, response);
        }
    }
}
