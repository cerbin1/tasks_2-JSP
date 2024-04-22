import db.dao.SubtaskDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.SubtaskService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class RemoveSubtask extends HttpServlet {
    private final SubtaskService subtaskService;

    public RemoveSubtask() {
        this.subtaskService = new SubtaskService(new SubtaskDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String subtaskId = request.getParameter("subtaskId");
        subtaskService.removeSubtask(subtaskId);
        response.sendRedirect(APP_BASE_PATH + "/editTask?taskId=" + request.getParameter("taskId"));
    }
}
