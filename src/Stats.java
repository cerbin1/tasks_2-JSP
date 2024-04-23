import db.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StatisticService;
import service.dto.TasksCountForDateDto;
import service.dto.TimeLoggedDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Stats extends HttpServlet {

    private final StatisticService statisticService;

    public Stats() {
        UserDao userDao = new UserDao();
        this.statisticService = new StatisticService(userDao, new TaskDao(), new SubtaskDao(), new NotificationDao(), new WorklogDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<TasksCountForDateDto> numberOfTasks = statisticService.getNumberOfTasks();
        StringBuilder dateValues = new StringBuilder("[");
        numberOfTasks.forEach(tasksCountForDateDto -> dateValues.append("\"").append(tasksCountForDateDto.getDate().toString()).append("\","));
        dateValues.append("]");
        String taskCounts = Arrays.toString(numberOfTasks.stream().map(TasksCountForDateDto::getCount).toArray());

        List<TimeLoggedDto> timeLoggedByUsers = statisticService.getTimeLoggedByUsers();
        String usernames = Arrays.toString(timeLoggedByUsers.stream().map(timeLoggedDto -> "\"" + timeLoggedDto.getName() + "\"").toArray());
        String timeLoggedCounts = Arrays.toString(timeLoggedByUsers.stream().map(TimeLoggedDto::getMinutesLogged).toArray());

        request.setAttribute("statistics", statisticService.getGeneralStatistics());
        request.setAttribute("dateValues", dateValues.toString());
        request.setAttribute("taskCounts", taskCounts);
        request.setAttribute("usernames", usernames);
        request.setAttribute("timeLoggedCounts", timeLoggedCounts);
        request.getServletContext().getRequestDispatcher("/stats.jsp").forward(request, response);
    }
}
