import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.*;
import service.dto.PriorityDto;
import service.dto.UserDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class CreateTask extends HttpServlet {
    private final UserService userService;
    private final PriorityService priorityService;
    private final TaskService taskService;
    private final NotificationService notificationService;
    private final TaskReminderService taskReminderService;

    public CreateTask() {
        this.taskService = new TaskService(new TaskDao());
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
        this.priorityService = new PriorityService(new PriorityDao());
        this.notificationService = new NotificationService(new NotificationDao());
        this.taskReminderService = new TaskReminderService(new TaskReminderDao());
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
            notificationService.createNotification("New Task", taskId, creatorId);
            taskReminderService.createTaskReminder(taskId, LocalDateTime.parse(deadline).minusHours(1));
            response.sendRedirect(APP_BASE_PATH + "/tasks");
        }
    }
}
