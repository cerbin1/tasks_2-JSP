package service;

import db.dao.TaskDao;
import service.dto.EditTaskDto;
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

    public EditTaskDto getTaskForEdit(String taskId) {
        return taskDao.findByIdForEdit(Long.parseLong(taskId));
    }

    public boolean updateTask(String taskId, String name, String deadline, String userId, String priorityId) {
        boolean success = taskDao.updateById(
                Long.parseLong(taskId),
                name,
                LocalDateTime.parse(deadline),
                Long.parseLong(userId),
                Long.parseLong(priorityId));
        return success;
    }

    public List<TaskDto> getUserTasks(String userId) {
        return taskDao.findAllByAssigneeId(Long.parseLong(userId));
    }

    public TaskDto getTask(String taskId) {
        return taskDao.findById(Long.parseLong(taskId));
    }
}
