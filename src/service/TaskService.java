package service;

import db.dao.SubtaskDao;
import db.dao.TaskDao;
import service.dto.EditTaskDto;
import service.dto.TaskDto;

import java.time.LocalDateTime;
import java.util.List;

public class TaskService {
    private final TaskDao taskDao;
    private final SubtaskDao subtaskDao;

    public TaskService(TaskDao taskDao, SubtaskDao subtaskDao) {
        this.taskDao = taskDao;
        this.subtaskDao = subtaskDao;
    }

    public Long create(String name, String deadline, String userId, String priorityId, String creatorId, String[] subtasks) {
        Long taskId = taskDao.createTask(name, LocalDateTime.parse(deadline), Long.parseLong(userId), Long.parseLong(priorityId), Long.parseLong(creatorId));
        if (subtasks != null) {
            subtaskDao.createSubtasks(taskId, subtasks);
        }
        return taskId;
    }

    public List<TaskDto> getAllTasks() {
        return taskDao.findAll();
    }

    public EditTaskDto getTaskForEdit(String taskId) {
        return taskDao.findByIdForEdit(Long.parseLong(taskId));
    }

    public boolean updateTask(String taskId, String name, String deadline, String userId, String priorityId,
                              String[] subtasksNames,
                              String[] subtasksIds,
                              String[] newSubtasks) {
        boolean success = taskDao.updateById(
                Long.parseLong(taskId),
                name,
                LocalDateTime.parse(deadline),
                Long.parseLong(userId),
                Long.parseLong(priorityId));
        if (subtasksIds != null) {
            subtaskDao.updateSubtasks(subtasksNames, subtasksIds);
        }
        if (newSubtasks != null) {
            subtaskDao.createSubtasks(Long.parseLong(taskId), newSubtasks);
        }
        return success;
    }

    public List<TaskDto> getUserTasks(String userId) {
        return taskDao.findAllByAssigneeId(Long.parseLong(userId));
    }

    public TaskDto getTask(String taskId) {
        return taskDao.findById(Long.parseLong(taskId));
    }

    public boolean removeTask(String taskId) {
        return taskDao.removeById(Long.parseLong(taskId));
    }

    public List<TaskDto> getTasksByName(String name) {
        return taskDao.findAllByName(name);
    }

    public void completeTask(String taskId) {
        taskDao.markAsCompleted(Long.parseLong(taskId));
    }
}
