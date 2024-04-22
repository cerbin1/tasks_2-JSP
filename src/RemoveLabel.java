import db.dao.LabelDao;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LabelService;

import java.io.IOException;

import static conf.ApplicationProperties.APP_BASE_PATH;

public class RemoveLabel extends HttpServlet {
    private final LabelService labelService;

    public RemoveLabel() {
        this.labelService = new LabelService(new LabelDao());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String labelId = request.getParameter("labelId");
        labelService.removeLabel(labelId);
        response.sendRedirect(APP_BASE_PATH + "/editTask?taskId=" + request.getParameter("taskId"));
    }
}
