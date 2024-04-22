import db.dao.LabelDao;
import db.dao.SubtaskDao;
import db.dao.TaskDao;
import db.dao.TaskFileDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;

import java.io.IOException;

public class SearchByName extends HttpServlet {

    private final TaskService taskService;

    public SearchByName() {
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), new TaskFileDao(), new LabelDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        request.setAttribute("tasks", taskService.getTasksByName(name));
        request.getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }
}
