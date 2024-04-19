package service;

import db.dao.TaskDao;
import service.dto.TaskDto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Long create(String name, String deadline, String userId, String priorityId, String creatorId) {
        return taskDao.createTask(name, LocalDateTime.parse(deadline), Long.parseLong(userId), Long.parseLong(priorityId), Long.parseLong(creatorId));
    }

    public List<TaskDto> getAllTasks() {
        return taskDao.findAll();
    }
}
