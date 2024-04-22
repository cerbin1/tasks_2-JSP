import db.dao.ChatMessageDao;
import db.dao.SubtaskDao;
import db.dao.TaskDao;
import db.dao.TaskFileDao;
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
    private final TaskFileDao taskFileDao;

    public TaskDetails() {
        this.taskFileDao = new TaskFileDao();
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), taskFileDao);
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
        request.setAttribute("files", taskFileDao.findAllForTaskId(Long.valueOf(taskId)));
        request.getServletContext().getRequestDispatcher("/taskDetails.jsp").forward(request, response);
    }
}
