import db.dao.NotificationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.NotificationService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class RemoveNotification extends HttpServlet {

    private final NotificationService notificationService;

    public RemoveNotification() {
        this.notificationService = new NotificationService(new NotificationDao());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String notificationId = request.getParameter("notificationId");
        if (notificationService.removeNotification(notificationId)) {
            response.sendRedirect(APP_BASE_PATH + "/notifications");
        } else {
            request.setAttribute("error", "Notification remove failed");
            request.getServletContext().getRequestDispatcher("/navbar.jsp").forward(request, response);
        }
    }
}
