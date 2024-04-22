import db.dao.ChatMessageDao;
import db.dao.SubtaskDao;
import db.dao.TaskDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ChatMessageService;
import service.SubtaskService;
import service.TaskService;
import service.dto.TaskDto;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;


public class TaskDetails extends HttpServlet {
    private final TaskService taskService;
    private final ChatMessageService chatMessageService;
    private final SubtaskService subtaskService;

    public TaskDetails() {
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao());
        this.chatMessageService = new ChatMessageService(new ChatMessageDao());
        this.subtaskService = new SubtaskService(new SubtaskDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        TaskDto task = taskService.getTask(taskId);
        request.setAttribute("task", task);
        request.setAttribute("chatMessages", chatMessageService.getTaskChatMessages(taskId));
        request.setAttribute("subtasks", subtaskService.getTaskSubtasks(taskId));
        request.getServletContext().getRequestDispatcher("/taskDetails.jsp").forward(request, response);
    }
}
