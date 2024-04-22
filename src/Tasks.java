import db.dao.SubtaskDao;
import db.dao.TaskDao;
import db.dao.TaskFileDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TaskService;
import service.dto.TaskDto;

import java.io.IOException;
import java.util.List;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class Tasks extends HttpServlet {
    private final TaskService taskService;

    public Tasks() {
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), new TaskFileDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<TaskDto> allTasks = taskService.getAllTasks();
        request.setAttribute("tasks", allTasks);
        request.getServletContext().getRequestDispatcher("/tasks.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String taskId = request.getParameter("taskId");
        taskService.completeTask(taskId);
        response.sendRedirect(APP_BASE_PATH + "/myTasks");
    }
}
