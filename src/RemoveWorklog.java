import db.dao.WorklogDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.WorklogService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class RemoveWorklog extends HttpServlet {
    private final WorklogService worklogService;

    public RemoveWorklog() {
        this.worklogService = new WorklogService(new WorklogDao());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String worklogId = request.getParameter("worklogId");
        worklogService.removeWorklog(worklogId);
        response.sendRedirect(APP_BASE_PATH + "/details?taskId=" + request.getParameter("taskId"));
    }
}
