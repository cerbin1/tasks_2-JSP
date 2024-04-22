import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;

import java.io.IOException;

public class SearchByLabel extends HttpServlet {

    private final TaskService taskService;

    public SearchByLabel() {
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), new TaskFileDao(), new LabelDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String label = request.getParameter("label");
        request.setAttribute("tasks", taskService.getTasksByLabel(label));
        request.setAttribute("categories", TaskCategory.listOfValues());
        request.getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }
}
