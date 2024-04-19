package service;

import db.dao.TaskDao;

import java.time.LocalDateTime;

public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Long create(String name, String deadline, String userId, String priorityId, String creatorId) {
        return taskDao.createTask(name, LocalDateTime.parse(deadline), Long.parseLong(userId), Long.parseLong(priorityId), Long.parseLong(creatorId));
    }
}
