import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.*;
import service.dto.TaskDto;

import java.io.IOException;


public class TaskDetails extends HttpServlet {
    private final TaskService taskService;
    private final ChatMessageService chatMessageService;
    private final SubtaskService subtaskService;
    private final TaskFileDao taskFileDao;
    private final LabelService labelService;
    private final WorklogService worklogService;

    public TaskDetails() {
        this.taskFileDao = new TaskFileDao();
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), taskFileDao, new LabelDao());
        this.chatMessageService = new ChatMessageService(new ChatMessageDao());
        this.subtaskService = new SubtaskService(new SubtaskDao());
        this.labelService = new LabelService(new LabelDao());
        this.worklogService = new WorklogService(new WorklogDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        TaskDto task = taskService.getTask(taskId);
        request.setAttribute("task", task);
        request.setAttribute("chatMessages", chatMessageService.getTaskChatMessages(taskId));
        request.setAttribute("subtasks", subtaskService.getTaskSubtasks(taskId));
        request.setAttribute("labels", labelService.getTaskLabels(taskId));
        request.setAttribute("files", taskFileDao.findAllForTaskId(Long.valueOf(taskId)));
        request.setAttribute("worklogs", worklogService.getTaskWorklogs(taskId));
        request.getServletContext().getRequestDispatcher("/taskDetails.jsp").forward(request, response);
    }
}
