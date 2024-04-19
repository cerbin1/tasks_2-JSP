import db.dao.PriorityDao;
import db.dao.TaskDao;
import db.dao.UserActivationLinkDao;
import db.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.EmailSendingService;
import service.PriorityService;
import service.TaskService;
import service.UserService;
import service.dto.EditTaskDto;
import service.dto.PriorityDto;
import service.dto.UserDto;

import java.io.IOException;
import java.util.List;

import static conf.ApplicationProperties.APP_BASE_PATH;


public class EditTask extends HttpServlet {
    private final UserService userService;
    private final TaskService taskService;
    private final PriorityService priorityService;

    public EditTask() {
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
        this.taskService = new TaskService(new TaskDao());
        this.priorityService = new PriorityService(new PriorityDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        EditTaskDto task = taskService.getTaskForEdit(taskId);
        List<UserDto> usersData = userService.getUsersData();
        List<PriorityDto> prioritiesData = priorityService.getPrioritiesData();

        request.setAttribute("task", task);
        request.setAttribute("users", usersData);
        request.setAttribute("priorities", prioritiesData);
        request.getServletContext().getRequestDispatcher("/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        String name = request.getParameter("name");
        String deadline = request.getParameter("deadline");
        String userId = request.getParameter("user");
        String priorityId = request.getParameter("priority");
        if (taskService.updateTask(taskId, name, deadline, userId, priorityId)) {
            response.sendRedirect(APP_BASE_PATH + "/tasks");
        } else {
            request.setAttribute("error", "Task edition failed");
            request.getServletContext().getRequestDispatcher("/navbar.jsp").forward(request, response);
        }
    }
}
