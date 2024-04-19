import db.dao.TaskDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;
import service.dto.TaskDto;

import java.io.IOException;
import java.util.List;

public class Tasks extends HttpServlet {
    private final TaskService taskService;

    public Tasks() {
        this.taskService = new TaskService(new TaskDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<TaskDto> allTasks = taskService.getAllTasks();
        request.setAttribute("tasks", allTasks);
        request.getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }
}
