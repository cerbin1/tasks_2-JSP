import db.dao.WorklogDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WorklogService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class CreateWorklog extends HttpServlet {
    private final WorklogService worklogService;

    public CreateWorklog() {
        this.worklogService = new WorklogService(new WorklogDao());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String date = request.getParameter("date");
        String minutes = request.getParameter("minutes");
        String comment = request.getParameter("comment");
        String creatorId = request.getParameter("creatorId");
        String taskId = request.getParameter("taskId");
        worklogService.createWorklog(date, minutes, comment, creatorId, taskId);
        response.sendRedirect(APP_BASE_PATH + "/details?taskId=" + taskId);
    }
}
