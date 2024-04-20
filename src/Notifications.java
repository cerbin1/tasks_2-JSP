import db.dao.NotificationDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.NotificationService;

import java.io.IOException;

public class Notifications extends HttpServlet {

    private final NotificationService notificationService;

    public Notifications() {
        this.notificationService = new NotificationService(new NotificationDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("notifications", notificationService.getAllNotifications());
        request.getServletContext().getRequestDispatcher("/notifications.jsp").forward(request, response);
    }
}
