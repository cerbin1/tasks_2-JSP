<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <head>
        <title>Task details</title>
        <link rel="stylesheet" href="bootstrap.min.css">
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class='container'>
            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Name</label>
                <p class="col-sm-8">${task.name}</p>
            </div>
            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Deadline</label>
                <p class="col-sm-8">${task.deadline}</p>
            </div>

            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Assignee</label>
                <p class="col-sm-8">${task.assignee}</p>
            </div>

            <div class="row">
                <label class="col-sm-4 col-form-label fw-bold">Priority</label>
                <p class="col-sm-8">${task.priority}</p>
            </div>
            <h1>Labels</h1>
            <h1>Category</h1>
            <h1>Subtasks</h1>
            <h1>Chat</h1>
            <h1>Files</h1>
            <h1>Worklogs</h1>
            <form action="/tasks_2-JSP/markAsCompleted?taskId=${task.id}" method="post" style="display: inline">
                <button type="submit" class="btn btn-success">Mark as completed</button>
            </form>
        </div>

</html>