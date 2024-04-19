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
import service.dto.PriorityDto;
import service.dto.UserDto;

import java.io.IOException;
import java.util.List;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class CreateTask extends HttpServlet {
    private final UserService userService;
    private final PriorityService priorityService;
    private final TaskService taskService;

    public CreateTask() {
        this.taskService = new TaskService(new TaskDao());
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
        this.priorityService = new PriorityService(new PriorityDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<UserDto> usersData = userService.getUsersData();
        List<PriorityDto> prioritiesData = priorityService.getPrioritiesData();

        request.setAttribute("users", usersData);
        request.setAttribute("priorities", prioritiesData);
        request.getServletContext().getRequestDispatcher("/createTask.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String deadline = request.getParameter("deadline");
        String creatorId = request.getParameter("user");
        String priorityId = request.getParameter("priority");
        String userId = (String) request.getSession(false).getAttribute("userId");
        Long taskId = taskService.create(name, deadline, userId, priorityId, creatorId);
        if (taskId == null) {
            request.setAttribute("error", "Task creation failed");
            request.getServletContext().getRequestDispatcher("/navbar.jsp").forward(request, response);
        } else {
            response.sendRedirect(APP_BASE_PATH + "/tasks");
        }
    }
}
