import db.dao.ChatMessageDao;
import db.dao.TaskDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ChatMessageService;
import service.TaskService;
import service.dto.TaskDto;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;


public class TaskDetails extends HttpServlet {
    private final TaskService taskService;
    private final ChatMessageService chatMessageService;

    public TaskDetails() {
        this.taskService = new TaskService(new TaskDao());
        this.chatMessageService = new ChatMessageService(new ChatMessageDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        TaskDto task = taskService.getTask(taskId);
        request.setAttribute("task", task);
        request.setAttribute("chatMessages", chatMessageService.getTaskChatMessages(taskId));
        request.getServletContext().getRequestDispatcher("/taskDetails.jsp").forward(request, response);
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
