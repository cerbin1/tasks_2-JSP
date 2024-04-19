<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Edit Task</title>
        <link rel="stylesheet" href="bootstrap.min.css">
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
                                <option value="${user.id}" selected="{user.id eq task.assigneeId}">${user.name}
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
                                <option value="${priority.id}" selected="{priority.id eq task.priorityId}">
                                    ${priority.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <h1>Labels</h1>
                <h1>Category</h1>
                <h1>Subtasks</h1>
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
