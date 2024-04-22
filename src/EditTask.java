import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import service.*;
import service.dto.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static conf.ApplicationProperties.APP_BASE_PATH;
import static conf.ApplicationProperties.FILE_UPLOADS_BASE_URL;


public class EditTask extends HttpServlet {
    private final UserService userService;
    private final TaskService taskService;
    private final PriorityService priorityService;
    private final SubtaskService subtaskService;
    private final TaskFileDao taskFileDao;

    public EditTask() {
        this.userService = new UserService(new UserDao(), new UserActivationLinkDao(), new EmailSendingService());
        this.taskService = new TaskService(new TaskDao(), new SubtaskDao(), new TaskFileDao());
        this.priorityService = new PriorityService(new PriorityDao());
        this.subtaskService = new SubtaskService(new SubtaskDao());
        this.taskFileDao = new TaskFileDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        EditTaskDto task = taskService.getTaskForEdit(taskId);
        List<UserDto> usersData = userService.getUsersData();
        List<PriorityDto> prioritiesData = priorityService.getPrioritiesData();
        List<SubtaskDto> subtasksData = subtaskService.getTaskSubtasks(taskId);
        List<TaskFileDto> files = taskFileDao.findAllForTaskId(Long.valueOf(taskId));

        request.setAttribute("task", task);
        request.setAttribute("users", usersData);
        request.setAttribute("priorities", prioritiesData);
        request.setAttribute("subtasks", subtasksData);
        request.setAttribute("files", files);
        request.getServletContext().getRequestDispatcher("/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskId = request.getParameter("taskId");
        String name = request.getParameter("name");
        String deadline = request.getParameter("deadline");
        String userId = request.getParameter("user");
        String priorityId = request.getParameter("priority");
        String[] subtasksNames = request.getParameterValues("subtasksNames[]");
        String[] subtasksIds = request.getParameterValues("subtasksIds[]");
        String[] newSubtasks = request.getParameterValues("newSubtasks[]");
        if (taskService.updateTask(taskId, name, deadline, userId, priorityId, subtasksNames, subtasksIds, newSubtasks)) {
            uploadNewFiles(request, taskId);
            response.sendRedirect(APP_BASE_PATH + "/tasks");
        } else {
            request.setAttribute("error", "Task edition failed");
            request.getServletContext().getRequestDispatcher("/navbar.jsp").forward(request, response);
        }
    }

    private void uploadNewFiles(HttpServletRequest request, String taskId) throws IOException, ServletException {
        Collection<Part> parts = request
                .getParts().stream()
                .filter(part -> part.getName().equals("files") && !part.getSubmittedFileName().isBlank())
                .collect(Collectors.toList());
        for (Part file : parts) {
            String fileName = file.getSubmittedFileName();
            String realPath = getServletContext().getRealPath(FILE_UPLOADS_BASE_URL);
            taskService.saveOrUpdateTaskFileInfo(fileName, file.getContentType(), taskId);
            file.write(realPath + File.separator + fileName);
        }
    }
}
