import db.dao.TaskDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class RemoveTask extends HttpServlet {
    private final TaskService taskService;

    public RemoveTask() {
        this.taskService = new TaskService(new TaskDao());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String taskId = request.getParameter("taskId");
        boolean removed = taskService.removeTask(taskId);
        if (removed) {
            response.sendRedirect(APP_BASE_PATH + "/tasks");
        } else {
            request.setAttribute("error", "Error while deleting task");
            request.getServletContext().getRequestDispatcher("/navbar.jsp").forward(request, response);
        }
    }
}
