import db.dao.TaskDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;
import service.dto.TaskDto;

import java.io.IOException;
import java.util.List;

public class MyTasks extends HttpServlet {
    private final TaskService taskService;

    public MyTasks() {
        this.taskService = new TaskService(new TaskDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<TaskDto> userTasks = taskService.getUserTasks((String) request.getSession().getAttribute("userId"));
        request.setAttribute("tasks", userTasks);
        request.getServletContext().getRequestDispatcher("/myTasks.jsp").forward(request, response);
    }
}