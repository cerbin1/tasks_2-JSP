import db.dao.UserActivationLinkDao;
import db.dao.UserDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.EmailSendingService;
import service.UserService;

import java.io.IOException;


public class Login extends HttpServlet {
    private final UserService userService;

    public Login() {
        userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String userId = userService.getUserIdByUserCredentials(username, password);
        if (userId.isBlank()) {
            throw new RuntimeException("Login");
        } else {
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            if (!userService.userIsLoggedIn(username, sessionId)) {
                userService.createLogin(username, sessionId);
            }
            session.setAttribute("username", username);
            session.setAttribute("userId", userId);
            response.sendRedirect("navbar.jsp");

        }
    }
}

