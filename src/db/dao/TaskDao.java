package db.dao;

import db.DbConnection;
import service.dto.TaskDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private static final String SQL_CREATE_TASK = "INSERT INTO task (\"name\", deadline, assignee_id, priority_id, creator_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String SQL_GET_ALL_TASKS = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue " +
//            "(SELECT COUNT(*) FROM subtask WHERE subtask.task_id = task.id) as subtasksCount, (SELECT COUNT(*) FROM worklog WHERE worklog.task_id = task.id) as worklogsCount " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "ORDER BY task.id";
    private static final String SQL_GET_USERNAME_TASKS = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "WHERE \"user\".id = ? " +
            "ORDER BY task.id";

    private static final String SQL_GET_TASK_FOR_EDIT_BY_ID = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            "task.assignee_id, task.priority_id " +
            "FROM task " +
            "WHERE task.id = ?";

    private static final String SQL_GET_TASK_BY_ID = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "WHERE task.id = ?";

    private static final String SQL_UPDATE_TASK = "UPDATE task SET name = ?, deadline = ?, assignee_id = ?, priority_id = ?, category = ? WHERE id = ?";

    private static final String SQL_DELETE_TASK_BY_ID = "DELETE FROM task WHERE id = ?";
    private static final String SQL_GET_TASKS_BY_NAME = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue, " +
            "(SELECT COUNT(*) FROM subtask WHERE subtask.task_id = task.id) as subtasksCount, (SELECT COUNT(*) FROM worklog WHERE worklog.task_id = task.id) as worklogsCount " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "WHERE task.\"name\" LIKE '%' || ? || '%' ORDER BY id";
    private static final String SQL_GET_TASKS_BY_CATEGORY = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue, " +
            "(SELECT COUNT(*) FROM subtask WHERE subtask.task_id = task.id) as subtasksCount, (SELECT COUNT(*) FROM worklog WHERE worklog.task_id = task.id) as worklogsCount " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "WHERE task.category LIKE '%' || ? || '%' ORDER BY id";
    private static final String SQL_GET_TASKS_BY_LABEL = "SELECT task.id, task.category, task.name, task.deadline, task.completed, task.complete_date," +
            " \"user\".name as assigneeName, priority.value as priorityValue, " +
            "(SELECT COUNT(*) FROM subtask WHERE subtask.task_id = task.id) as subtasksCount, (SELECT COUNT(*) FROM worklog WHERE worklog.task_id = task.id) as worklogsCount " +
            "FROM task " +
            "JOIN priority ON task.priority_id = priority.id " +
            "JOIN \"user\" ON task.assignee_id = \"user\".id " +
            "WHERE task.id IN (SELECT task_label.task_id FROM task_label WHERE task_label.name = ?) ORDER BY id";
    private static final String SQL_MARK_TASK_AS_COMPLETED = "UPDATE task SET completed = true, complete_date = NOW() WHERE id = ?";
    private static final String SQL_GET_NUMBER_OF_CREATED_TASKS = "SELECT COUNT(*) FROM task";
    private static final String SQL_GET_NUMBER_OF_COMPLETED_TASKS = "SELECT COUNT(*) FROM task WHERE completed = true";

    public Long createTask(String name, LocalDateTime deadline, Long userId, Long priorityId, Long creatorId) {
        DbConnection dbConnection = new DbConnection();
        Connection connection = dbConnection.createConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_TASK)) {
            preparedStatement.setString(1, name);
            preparedStatement.setObject(2, deadline);
            preparedStatement.setLong(3, userId);
            preparedStatement.setLong(4, priorityId);
            preparedStatement.setLong(5, creatorId);

            if (preparedStatement.execute() && preparedStatement.getResultSet().next()) {
                connection.close();
                return preparedStatement.getResultSet().getLong(1);
            } else {
                connection.close();
                throw new SQLException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TaskDto> findAll() {
        DbConnection dbConnection = new DbConnection();
        try (Connection connection = dbConnection.createConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_TASKS)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                List<TaskDto> tasks = new ArrayList<>();
                while (resultSet.next()) {
                    tasks.add(new TaskDto(resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getObject("deadline", LocalDateTime.class),
                            resultSet.getString("assigneeName"),
                            resultSet.getString("priorityValue"),
                            resultSet.getBoolean("completed"),
                            resultSet.getObject("complete_date", LocalDateTime.class),
                            resultSet.getString("category")
                    ));
                }
                return tasks;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
