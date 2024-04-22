import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;

import java.io.IOException;

public class SearchByCategory extends HttpServlet {

    private final TaskService taskService;

    public SearchByCategory() {
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), new TaskFileDao(), new LabelDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String category = request.getParameter("category");
        request.setAttribute("tasks", taskService.getTasksByCategory(category));
        request.setAttribute("categories", TaskCategory.listOfValues());
        request.getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }
}
