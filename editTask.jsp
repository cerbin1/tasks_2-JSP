<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Edit Task</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <script>
            document.addEventListener('DOMContentLoaded', function () {
                var addSubtaskButton = document.getElementById('addSubtask');
                addSubtaskButton.addEventListener('click', function () {
                    var newSubtask = document.createElement('input');
                    newSubtask.type = 'text';
                    newSubtask.classList.add('form-control');
                    newSubtask.style.textAlign = 'center';
                    newSubtask.name = 'newSubtasks[]';
                    newSubtask.placeholder = 'Subtask name';

                    var subtasksContainer = document.getElementById('subtasks');
                    subtasksContainer.appendChild(newSubtask);
                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class='container'>
            <form action="/tasks_2-JSP/editTask?taskId=${task.id}" method="post">
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-10">
                        <input required type="text" class="form-control" id="name" name="name" value="${task.name}" />
                    </div>
                </div>
                <div class="form-group row">
                    <label for="deadline" class="col-sm-2 col-form-label">Deadline</label>
                    <div class="col-sm-10">
                        <input required class="form-control" id="deadline" name="deadline" type="datetime-local"
                            value="${task.deadline}" />
                    </div>
                </div>

                <div class=" form-group row">
                    <label for="user" class="col-sm-2 col-form-label">Assignee</label>
                    <div class="col-sm-10">
                        <select class="form-select" id="user" name="user">
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}" <c:if test="${user.id eq task.assigneeId}">selected</c:if>>
                                    ${user.name}
                                    ${user.surname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label for="priority" class="col-sm-2 col-form-label">Priority</label>
                    <div class="col-sm-10">
                        <select class="form-select" id="priority" name="priority">
                            <c:forEach var="priority" items="${priorities}">
                                <option value="${priority.id}" <c:if test="${priority.id eq task.priorityId}">selected
                                    </c:if>>
                                    ${priority.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <h1>Labels</h1>
                <h1>Category</h1>
                <h1>Subtasks</h1>
                <div class="d-flex align-items-center justify-content-center">
                    <div id="subtasks" class="form-group col-md-3">
                        <c:forEach var="subtask" items="${subtasks}">
                            <div class="input-group">
                                <input class="form-control col-md-9" style="text-align: center;" name="subtasksNames[]"
                                    value="${subtask.name}" />
                                <input type="hidden" name="subtasksIds[]" value="${subtask.id}" />
                                <a href="/tasks_2-JSP/removeSubtask?taskId=${task.id}&subtaskId=${subtask.id}" type="submit"
                                    class="btn btn-danger col-md-3">Remove</a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <button id="addSubtask" type="button" class="btn btn-success">Add subtask</button>
                <h1>Files upload</h1>
                <h1>Worklogs</h1>
                <div class="form-group row">
                    <div class='form-control'>
                        <a href="/tasks_2-JSP/tasks" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">Update</button>
                    </div>
                </div>
            </form>
        </div>

</html>